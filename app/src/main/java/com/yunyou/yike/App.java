package com.yunyou.yike;

import android.app.Application;
import android.content.res.Configuration;

import com.yunyou.yike.dagger2.AppCompcoent;
import com.yunyou.yike.dagger2.DaggerAppCompcoent;
import com.yunyou.yike.utils.LogUtils;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public class App extends Application {
    private AppCompcoent mAppCompcoent;//全局app 依赖注入

    @Override
    public void onCreate() {
        super.onCreate();
        mAppCompcoent = DaggerAppCompcoent.create();
        AppManager.initApp(this);

    }

    public AppCompcoent getAppCompcoent() {
        return mAppCompcoent;
    }

    @Override
    public void onTerminate() {
        LogUtils.d("程序终止的时候执行");
        appOut();
        super.onTerminate();
    }

    /**
     * 退出程序时调用
     */
    public static void appOut() {
        if (AppManager.getInstance() != null) {
            AppManager.getInstance().logOutApp();
        }
    }

    @Override
    public void onLowMemory() {
        LogUtils.d("低内存的时候执行");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtils.d("程序在内存清理的时候执行");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.d("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

}
