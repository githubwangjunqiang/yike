package com.yunyou.yike.fragment.order;

import android.os.Handler;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class OrderFragmentModel implements IModel.IOrderFragmentModel {
    @Override
    public void getOrder(IModel.AsyCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Order> list = new ArrayList<Order>();
                for (int i = 0; i < 10; i++) {
                    list.add(new Order("", 0));
                }
                list.add(new Order("正在加载.......", Integer.MAX_VALUE - 1));
                callback.success(list);
            }
        }, 2000);

    }
}
