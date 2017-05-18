package com.yunyou.yike.model;

import android.os.Handler;

import com.yunyou.yike.entity.Feel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class FeelFragmentModel implements IModel.IFeelFragmentModel {
    @Override
    public void getFeelData(final IModel.AsyCallback callback) {
        callback.startModel(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Feel> list = new ArrayList<Feel>();
                for (int i = 0; i < 10; i++) {
                    List<String> strings = new ArrayList<String>();
                    for (int j = 0; j < 6; j++) {
                        strings.add("");
                    }
                    list.add(new Feel(strings));
                }
                callback.success(list);
            }
        }, 1500);

    }
}
