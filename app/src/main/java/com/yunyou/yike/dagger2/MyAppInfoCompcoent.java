package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.MyAppInfoActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/6/16.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface MyAppInfoCompcoent {
    void inject(MyAppInfoActivity appInfoActivity);
}
