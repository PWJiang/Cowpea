package com.jiangpingwei.cowpea;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface BasePresenter {
    void subscribe(String dataType, int pageNO);
    void unsubscribe();
}
