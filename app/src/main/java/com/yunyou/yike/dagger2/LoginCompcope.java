package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.LoginActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/26.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface LoginCompcope {

    void inject(LoginActivity activity);

}
