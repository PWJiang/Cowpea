package com.jiangpingwei.cowpea.data;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/10.
 */

public interface ChongdianDataSource {
    interface getDataCallback {
        void onGetDataed(List<Results> resultses);

        void onFail();
    }

    void getData(getDataCallback callback, String dataType, int pageNO);
}
