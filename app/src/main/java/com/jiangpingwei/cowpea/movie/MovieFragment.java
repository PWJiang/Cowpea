package com.jiangpingwei.cowpea.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiangpingwei.cowpea.OnRecyclerItemClickListener;
import com.jiangpingwei.cowpea.R;
import com.jiangpingwei.cowpea.WebActivity;
import com.jiangpingwei.cowpea.data.MovieRepository;
import com.jiangpingwei.cowpea.data.MovieResults;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangpingwei on 2017/2/22.
 */

public class MovieFragment extends Fragment implements MovieContract.View {

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swl_movie)
    SwipeRefreshLayout swlMovie;

    private MovieContract.Presenter mPresenter;

    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter movieAdapter;

    private int lastVisibleItem;

    private int pageNO = 1;

    private int dataType = 1;//1:热门；13：搞笑；19：女神；59：美食；18：宝宝

    public MovieFragment() {
        mPresenter = new MoviePresenter(this, new MovieRepository());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);

        swlMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe(true, String.valueOf(dataType), 1);
            }
        });

        rvMovie.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == movieAdapter.getItemCount()) {
                    mPresenter.subscribe(false, String.valueOf(dataType), pageNO++);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity());
        movieAdapter = new MovieAdapter();

        rvMovie.setLayoutManager(linearLayoutManager);
        rvMovie.setAdapter(movieAdapter);

        rvMovie.addOnItemTouchListener(new OnRecyclerItemClickListener(rvMovie) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                MovieAdapter.MovieViewHolder movieViewHolder = (MovieAdapter.MovieViewHolder) viewHolder;
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra("URL", movieViewHolder.URL);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tl_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_hot:
                        dataType = 1;
                        break;
                    case R.id.menu_funny:
                        dataType = 13;
                        break;
                    case R.id.menu_goddess:
                        dataType = 19;
                        break;
                    case R.id.menu_food:
                        dataType = 59;
                        break;
                    case R.id.menu_baby:
                        dataType = 18;
                        break;
                }
                mPresenter.subscribe(true, String.valueOf(dataType), 1);
                return true;
            }
        });

        mPresenter.subscribe(false, String.valueOf(dataType), 1);

        return view;
    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        swlMovie.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swlMovie.setRefreshing(false);
    }

    @Override
    public void showMobvies() {
        movieAdapter.setList(mPresenter.getData(), getActivity());
    }

    @Override
    public void showError() {
        Snackbar.make(swlMovie, R.string.error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNewMovies() {
        movieAdapter.setNewList(mPresenter.getData(), getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    public static class MovieAdapter extends RecyclerView.Adapter {

        private List<MovieResults> mList = new ArrayList<>();
        private Context context;

        public void setList(List<MovieResults> list, Context context) {
            mList.addAll(list);
            this.context = context;
            notifyDataSetChanged();
        }

        public void setNewList(List<MovieResults> list, Context context) {
            mList.clear();
            mList.addAll(list);
            this.context = context;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            if (!TextUtils.isEmpty(mList.get(position).getAvatar())) {
                movieViewHolder.ivItemMovie.setVisibility(View.VISIBLE);
                Glide.with(context).load(mList.get(position).getAvatar()).thumbnail(0.1f).fitCenter().into(movieViewHolder.ivItemMovie);
            } else {
                movieViewHolder.ivItemMovie.setVisibility(View.GONE);
            }

            movieViewHolder.tvItemMovieDesc.setText(mList.get(position).getCaption());
            movieViewHolder.tvItemMovieTime.setText(mList.get(position).getCreatedAt());

            movieViewHolder.URL = mList.get(position).getUrl();
        }

        @Override
        public int getItemCount() {
            if (null == mList) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public static class MovieViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_item_movie)
            ImageView ivItemMovie;
            @BindView(R.id.tv_item_movie_desc)
            TextView tvItemMovieDesc;
            @BindView(R.id.tv_item_movie_time)
            TextView tvItemMovieTime;

            private String URL;

            private MovieViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }
        }
    }
}
