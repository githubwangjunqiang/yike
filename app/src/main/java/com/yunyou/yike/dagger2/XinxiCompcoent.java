package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.XinxiGuanliActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/6/9.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface XinxiCompcoent {
    void inject(XinxiGuanliActivity xinxiGuanliActivity);
}
