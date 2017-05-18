package com.yunyou.yike.model;

import android.os.Handler;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentModel implements IModel.IMyFragmentModel {
    @Override
    public void getUserInfo(final IModel.AsyCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.success(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.error(e.getMessage());
                }
            }
        }, 1000);
    }
}
