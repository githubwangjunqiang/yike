package com.yunyou.yike.dagger2;

import com.yunyou.yike.fragment.order.fragment.AllOrderFragment;
import com.yunyou.yike.fragment.order.fragment.CancelOrderFragment;
import com.yunyou.yike.fragment.order.fragment.FinishedOrderFragment;
import com.yunyou.yike.fragment.order.fragment.UnfinishedOrderFragment;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/6/9.
 */
@FragmentScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface AllOrderCompcoet {
    void inject(AllOrderFragment fragment);

    void inject(CancelOrderFragment fragment);

    void inject(FinishedOrderFragment fragment);

    void inject(UnfinishedOrderFragment fragment);
}
