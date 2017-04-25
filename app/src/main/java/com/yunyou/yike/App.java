package com.yunyou.yike;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public class App extends Application {

    /**
     * 全局上下文
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
