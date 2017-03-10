package com.jiangpingwei.cowpea.zhijin;

import com.jiangpingwei.cowpea.zhijin.data.Photos;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface ZhijinService {
    @GET("福利/10/{pageNO}")
    Observable<Photos> getPhote(@Path("pageNO") int pageNO);
}
