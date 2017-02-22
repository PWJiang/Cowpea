package com.jiangpingwei.cowpea;

/**
 * Created by jiangpingwei on 2017/2/21.
 */

public interface BaseView<T> {
    // 规定View必须要实现setPresenter方法，则View中保持对Presenter的引用。
    void setPresenter(T presenter);
}
