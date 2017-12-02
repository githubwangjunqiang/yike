package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.OrderInfoActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/6/20.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface OrderInfoCompcoent {
    void inject(OrderInfoActivity activity);
}
