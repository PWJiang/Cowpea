package com.jiangpingwei.cowpea.data;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface MovieDataSource {

    interface GetMoviesCallback {
        void onGetMovied(List<MovieResults> resultses);

        void onFail();
    }

    void getMovies(GetMoviesCallback callback, int id, String topic_name, int count, int page);

}
