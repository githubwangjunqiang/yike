package com.yunyou.yike.dagger2;

import com.yunyou.yike.fragment.my.MyFragment;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/6/9.
 */
@FragmentScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface MyFragmentCompcoent {
    void inject(MyFragment fragment);
}
