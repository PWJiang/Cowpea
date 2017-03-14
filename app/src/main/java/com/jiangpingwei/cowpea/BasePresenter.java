package com.jiangpingwei.cowpea;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface BasePresenter {
    void subscribe(boolean isNewData, String dataType, int pageNO);

    void unsubscribe();
}
