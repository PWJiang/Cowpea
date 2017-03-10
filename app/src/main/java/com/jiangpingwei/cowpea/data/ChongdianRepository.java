package com.jiangpingwei.cowpea.data;

import android.util.Log;

import com.jiangpingwei.cowpea.chongdian.ChongdianService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public class ChongdianRepository implements ChongdianDataSource {
    @Override
    public void getData(final getDataCallback callback, String dataType, int pageNO) {
        String baseUrl = "http://gank.io/api/data/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChongdianService chongdianService = retrofit.create(ChongdianService.class);

        chongdianService.getChongdianData(dataType, pageNO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<Data>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super Data> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Data value) {
                        if (value.getError()) {
                            callback.onFail();
                        } else {
                            callback.onGetDataed(value.getResults());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", "ERROR");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
