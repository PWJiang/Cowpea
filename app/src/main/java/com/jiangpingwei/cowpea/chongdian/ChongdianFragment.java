package com.jiangpingwei.cowpea.chongdian;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiangpingwei.cowpea.R;
import com.jiangpingwei.cowpea.data.ChongdianRepository;
import com.jiangpingwei.cowpea.data.Results;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangpingwei on 2017/2/22.
 */

public class ChongdianFragment extends Fragment implements ChongdianContract.View {
    @BindView(R.id.rv_chongdian)
    RecyclerView rvChongdian;
    @BindView(R.id.swl_chongdian)
    SwipeRefreshLayout swlChongdian;

    private ChongdianContract.Present mPresenter;

    private LinearLayoutManager linearLayoutManager;
    private ChongdianAdapter chongdianAdapter;

    private Random random = new Random();

    private String dataType = "Android";

    public ChongdianFragment() {
        mPresenter = new ChongdianPresenter(this, new ChongdianRepository());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chongdian, container, false);
        ButterKnife.bind(this, view);

        swlChongdian.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.subscribe(dataType, random.nextInt(10));
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity());
        chongdianAdapter = new ChongdianAdapter();

        rvChongdian.setLayoutManager(linearLayoutManager);
        rvChongdian.setAdapter(chongdianAdapter);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tl_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_android:
                        dataType = "Android";
                        break;
                    case R.id.menu_ios:
                        dataType = "iOS";
                        break;
                    case R.id.menu_qianduan:
                        dataType = "前端";
                        break;
                    case R.id.menu_expand_resources:
                        dataType = "拓展资源";
                        break;
                }
                mPresenter.subscribe(dataType, random.nextInt(10));
                return true;
            }
        });

        return view;
    }

    @Override
    public void setPresenter(ChongdianContract.Present presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        swlChongdian.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swlChongdian.setRefreshing(false);
    }

    @Override
    public void showDatas() {
        chongdianAdapter.setList(mPresenter.getData(), getActivity());
    }

    @Override
    public void showError() {
        Snackbar.make(swlChongdian, R.string.error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(dataType, random.nextInt(10));
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    public static class ChongdianAdapter extends RecyclerView.Adapter {

        private List<Results> mList;
        private Context context;

        public void setList(List<Results> list, Context context) {
            this.mList = list;
            this.context = context;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chongdian, parent, false);
            return new ChongdianViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ChongdianViewHolder chongdianViewHolder = (ChongdianViewHolder) holder;

            if (null != mList.get(position).getImages() && mList.size() > 0) {
                chongdianViewHolder.ivItemChongdian.setVisibility(View.VISIBLE);
                Glide.with(context).load(mList.get(position).getImages().get(0)).fitCenter().into(chongdianViewHolder.ivItemChongdian);
            } else {
                chongdianViewHolder.ivItemChongdian.setVisibility(View.GONE);
            }

            chongdianViewHolder.tvItemChongdianDesc.setText(mList.get(position).getDesc());
            chongdianViewHolder.tvItemChongdianTime.setText((mList.get(position).getPublishedAt()));
        }

        @Override
        public int getItemCount() {
            if (null == mList) {
                return 0;
            } else {
                return mList.size();
            }
        }

        public static class ChongdianViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_item_chongdian)
            ImageView ivItemChongdian;
            @BindView(R.id.tv_item_chongdian_desc)
            TextView tvItemChongdianDesc;
            @BindView(R.id.tv_item_chongdian_time)
            TextView tvItemChongdianTime;

            private ChongdianViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }
        }
    }
}
