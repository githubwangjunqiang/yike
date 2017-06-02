package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.RegisterActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/23.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface DaggerCompcoent {
    void inject(RegisterActivity activity);
}
