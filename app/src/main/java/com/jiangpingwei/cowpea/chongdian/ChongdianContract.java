package com.jiangpingwei.cowpea.chongdian;

import com.jiangpingwei.cowpea.BasePresenter;
import com.jiangpingwei.cowpea.BaseView;
import com.jiangpingwei.cowpea.data.Results;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public interface ChongdianContract {

    interface View extends BaseView<Present> {
        void showProgress();

        void hideProgress();

        void showDatas();

        void showError();

        void showNewDatas();
    }

    interface Present extends BasePresenter {

        List<Results> getData();
    }
}
