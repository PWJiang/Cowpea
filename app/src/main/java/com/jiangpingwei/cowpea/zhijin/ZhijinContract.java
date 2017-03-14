package com.jiangpingwei.cowpea.zhijin;

import com.jiangpingwei.cowpea.BasePresenter;
import com.jiangpingwei.cowpea.BaseView;
import com.jiangpingwei.cowpea.data.Results;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface ZhijinContract {
    interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void showPhotos();

        void showError();

        void showNewPhotos();
    }

    interface Presenter extends BasePresenter{

        List<Results> getData();
    }
}
