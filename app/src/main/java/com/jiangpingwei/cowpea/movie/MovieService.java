package com.jiangpingwei.cowpea.movie;

import com.jiangpingwei.cowpea.data.MovieResults;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jiangpingwei on 2017/3/14.
 */

public interface MovieService {
    @GET("channels_topics_timeline.json")
    Observable<List<MovieResults>> getMovie(@Query("id") int id, @Query("topic_name") String topic_name, @Query("count") int count, @Query("page") int page);
}
