package com.jiangpingwei.cowpea.chongdian;

import com.jiangpingwei.cowpea.data.Data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public interface ChongdianService {
    @GET("{dataType}/10/{pageNO}")
    Observable<Data> getChongdianData(@Path("dataType") String dataType, @Path("pageNO") int pageNO);
}
