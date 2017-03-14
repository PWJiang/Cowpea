package com.jiangpingwei.cowpea.movie;

import com.jiangpingwei.cowpea.BasePresenter;
import com.jiangpingwei.cowpea.BaseView;
import com.jiangpingwei.cowpea.data.MovieResults;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/14.
 */

public interface MovieContract {
    interface View extends BaseView<Presenter> {
        void showProgress();

        void hideProgress();

        void showMobvies();

        void showError();

        void showNewMovies();
    }

    interface Presenter extends BasePresenter {
        List<MovieResults> getData();
    }
}
