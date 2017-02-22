package com.jiangpingwei.cowpea;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiangpingwei on 2017/2/21.
 */

@Module
public final class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context mContext){
        this.mContext = mContext;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }
}
