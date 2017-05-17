package com.yunyou.yike.fragment.my;

import android.os.Handler;

import com.yunyou.yike.Interface.IModel;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentModel implements IModel.IMyFragmentModel {
    @Override
    public void getUserInfo(IModel.AsyCallback callback) {
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
