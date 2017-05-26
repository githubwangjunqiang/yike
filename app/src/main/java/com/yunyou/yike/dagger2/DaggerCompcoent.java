package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.activity.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/23.
 */
@Singleton
@Component(modules = RegisterMobule.class)
public interface DaggerCompcoent {
    void inject(RegisterActivity activity);

    void inject(LoginActivity activity);
}
