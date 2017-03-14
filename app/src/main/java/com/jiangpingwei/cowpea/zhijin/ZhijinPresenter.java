package com.jiangpingwei.cowpea.zhijin;


import com.jiangpingwei.cowpea.data.Results;
import com.jiangpingwei.cowpea.data.ZhijinDataSource;
import com.jiangpingwei.cowpea.data.ZhijinRepository;

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
    public void subscribe(boolean isNewData, String dataType, int pageNO) {
        loadPhotos(isNewData, pageNO);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.dispose();
    }

    private void loadPhotos(final boolean isNewData, final int pageNO) {
        mView.showProgress();

        mZhijinRepository.getPhotos(new ZhijinDataSource.GetPhotosCallback() {
            @Override
            public void onGetPhotoed(List<Results> resultses) {
                data = resultses;
                if(isNewData){
                    mView.showNewPhotos();
                }else {
                    mView.showPhotos();
                }
                mView.hideProgress();
            }

            @Override
            public void onFail() {
                mView.showError();
            }
        }, pageNO);
    }
}
