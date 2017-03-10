package com.jiangpingwei.cowpea.zhijin.data;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public interface ZhijinDataSource {

    interface GetPhotosCallback {
        void onGetPhotoed(List<Results> resultses);

        void onFail();
    }

    void getPhotos(GetPhotosCallback callback, int pageNO);

}
