package com.yunyou.yike.dagger2;

import com.yunyou.yike.fragment.home.HomeFragment;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/27.
 */
@FragmentScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface HomeFragmentCompcoent {
    void inJect(HomeFragment homeFragment);
}
