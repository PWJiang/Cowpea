package com.jiangpingwei.cowpea.movie;

import com.jiangpingwei.cowpea.data.MovieDataSource;
import com.jiangpingwei.cowpea.data.MovieRepository;
import com.jiangpingwei.cowpea.data.MovieResults;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * Created by jiangpingwei on 2017/3/14.
 */

public class MoviePresenter implements MovieContract.Presenter {

    private MovieRepository mMovieRepository;
    private MovieContract.View mView;

    private ArrayCompositeSubscription mSubscriptions;

    private List<MovieResults> data = new ArrayList<>();

    public List<MovieResults> getData() {
        return data;
    }

    public MoviePresenter(MovieContract.View view, MovieRepository movieRepository) {
        mView = view;
        mMovieRepository = movieRepository;

        mSubscriptions = new ArrayCompositeSubscription(1);

        mView.setPresenter(this);
    }

    @Override
    public void subscribe(boolean isNewData, String dataType, int pageNO) {
        loadMovies(isNewData, dataType, pageNO);
    }


    @Override
    public void unsubscribe() {

    }

    private void loadMovies(final boolean isNewData, String dataType, int pageNO) {
        mView.showProgress();

        mMovieRepository.getMovies(new MovieDataSource.GetMoviesCallback() {
            @Override
            public void onGetMovied(List<MovieResults> resultses) {
                data = resultses;
                if (isNewData) {
                    mView.showNewMovies();
                } else {
                    mView.showMobvies();
                }
                mView.hideProgress();
            }

            @Override
            public void onFail() {
                mView.showError();
            }
        }, Integer.parseInt(dataType), "", 10, pageNO);
    }
}
