package com.jiangpingwei.cowpea.zhijin;


import android.util.Log;

import com.jiangpingwei.cowpea.zhijin.data.Results;
import com.jiangpingwei.cowpea.zhijin.data.ZhijinDataSource;
import com.jiangpingwei.cowpea.zhijin.data.ZhijinRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public class ZhijinPresenter implements ZhijinContract.Presenter {

    private ZhijinRepository mZhijinRepository;
    private ZhijinContract.View mView;

    private ArrayCompositeSubscription mSubscriptions;

    private List<Results> data = new ArrayList<>();

    public List<Results> getData() {
        return data;
    }

    public ZhijinPresenter(ZhijinContract.View view, ZhijinRepository zhijinRepository) {
        mView = view;
        mZhijinRepository = zhijinRepository;

        mSubscriptions = new ArrayCompositeSubscription(1);

        mView.setPresenter(this);
    }

    @Override
    public void subscribe(int pageNO) {
        loadPhotos(pageNO);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.dispose();
    }

    private void loadPhotos(final int pageNO) {
        mView.showProgress();

        mZhijinRepository.getPhotos(new ZhijinDataSource.GetPhotosCallback() {
            @Override
            public void onGetPhotoed(List<Results> resultses) {
                data = resultses;
                mView.showPhotos();
            }

            @Override
            public void onFail() {
                mView.showError();
            }
        }, pageNO);

        mView.hideProgress();
    }
}
