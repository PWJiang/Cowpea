package com.jiangpingwei.cowpea.chongdian;

import com.jiangpingwei.cowpea.data.ChongdianDataSource;
import com.jiangpingwei.cowpea.data.ChongdianRepository;
import com.jiangpingwei.cowpea.data.Results;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public class ChongdianPresenter implements ChongdianContract.Present {

    private ChongdianRepository mChongdianRepository;
    private ChongdianContract.View mView;

    private ArrayCompositeSubscription mSubscriptions;

    private List<Results> data = new ArrayList<>();

    public List<Results> getData() {
        return data;
    }

    public ChongdianPresenter(ChongdianContract.View view, ChongdianRepository chongdianRepository) {
        mView = view;
        mChongdianRepository = chongdianRepository;

        mSubscriptions = new ArrayCompositeSubscription(1);

        mView.setPresenter(this);
    }

    @Override
    public void subscribe(boolean isNewData, String dataType, int pageNO) {
        loadData(isNewData, dataType, pageNO);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.dispose();
    }

    private void loadData(final boolean isNewData, String dataType, int pageNO) {
        mView.showProgress();

        mChongdianRepository.getData(new ChongdianDataSource.getDataCallback() {
            @Override
            public void onGetDataed(List<Results> resultses) {
                data = resultses;
                if(isNewData){
                    mView.showNewDatas();
                }else {
                    mView.showDatas();
                }

                mView.hideProgress();
            }

            @Override
            public void onFail() {
                mView.showError();
            }
        }, dataType, pageNO);
    }
}
