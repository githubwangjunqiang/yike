package com.yunyou.yike.dagger2;

import com.yunyou.yike.activity.DecorationWorkerActivity;

import dagger.Component;

/**
 * Created by ${王俊强} on 2017/5/27.
 */
@ActivityScope
@Component(modules = PresenterMobule.class, dependencies = AppCompcoent.class)
public interface WorkerCompcoent {
    void inJect(DecorationWorkerActivity activity);
}
