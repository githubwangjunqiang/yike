package com.yunyou.yike.dagger2;

import com.yunyou.yike.http.entity.RxApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/26.
 */
@Singleton
@Component(modules = AppMobule.class)
public interface AppCompcoent {
    RxApi providerRxApi();

}
