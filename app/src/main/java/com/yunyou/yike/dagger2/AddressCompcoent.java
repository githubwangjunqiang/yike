package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.AddressActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/31.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface AddressCompcoent {

    void inject(AddressActivity addressActivity);
}
