package com.jiangpingwei.cowpea.zhijin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiangpingwei.cowpea.OnRecyclerItemClickListener;
import com.jiangpingwei.cowpea.PhotoActivity;
import com.jiangpingwei.cowpea.R;
import com.jiangpingwei.cowpea.data.Results;
import com.jiangpingwei.cowpea.data.ZhijinRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangpingwei on 2017/2/22.
 */

public class ZhijinFragment extends Fragment implements ZhijinContract.View {
    @BindView(R.id.rv_zhijin)
    RecyclerView rvZhijin;
    @BindView(R.id.swl_zhijin)
    SwipeRefreshLayout swlZhijin;

    private ZhijinContract.Presenter mPresenter;

    private GridLayoutManager gridLayoutManager;
    private ZhijinAdapter zhijinAdapter;

    private int lastVisibleItem;

    private int pageNO = 1;

    public ZhijinFragment() {
        mPresenter = new ZhijinPresenter(this, new ZhijinRepository());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhijin, container, false);
        ButterKnife.bind(this, view);

        swlZhijin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe(true, null, 1);
            }
        });

        rvZhijin.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == zhijinAdapter.getItemCount()) {
                    mPresenter.subscribe(false, null, pageNO++);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        zhijinAdapter = new ZhijinAdapter();

        rvZhijin.setLayoutManager(gridLayoutManager);
        rvZhijin.setAdapter(zhijinAdapter);

        rvZhijin.addOnItemTouchListener(new OnRecyclerItemClickListener(rvZhijin) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                ZhijinAdapter.ZhijinViewHolder zhijinViewHolder = (ZhijinAdapter.ZhijinViewHolder) viewHolder;
                Intent intent = new Intent();
                intent.setClass(getActivity(), PhotoActivity.class);
                intent.putExtra("IMAGEURL", zhijinViewHolder.imageURL);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });

        mPresenter.subscribe(false, null, 1);

        return view;
    }

    @Override
    public void setPresenter(ZhijinContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        swlZhijin.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swlZhijin.setRefreshing(false);
    }

    @Override
    public void showPhotos() {
        zhijinAdapter.setList(mPresenter.getData(), getActivity());
    }

    @Override
    public void showError() {
        Snackbar.make(swlZhijin, R.string.error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNewPhotos() {
        zhijinAdapter.setNewList(mPresenter.getData(), getActivity());
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

    public static class ZhijinAdapter extends RecyclerView.Adapter {

        private List<Results> mList = new ArrayList<>();
        private Context context;

        public void setList(List<Results> list, Context context) {
            mList.addAll(list);
            this.context = context;
            notifyDataSetChanged();
        }

        public void setNewList(List<Results> list, Context context) {
            mList.clear();
            mList.addAll(list);
            this.context = context;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhijin, parent, false);
            return new ZhijinViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ZhijinViewHolder zhijinViewHolder = (ZhijinViewHolder) holder;
            Glide.with(context).load(mList.get(position).getUrl()).centerCrop().into(zhijinViewHolder.ivItemZhijin);

            zhijinViewHolder.imageURL = mList.get(position).getUrl();
        }

        @Override
        public int getItemCount() {
            if (null == mList) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public static class ZhijinViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_item_zhijin)
            ImageView ivItemZhijin;

            private String imageURL;

            private ZhijinViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }
        }
    }

}
