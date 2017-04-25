package com.yunyou.yike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yunyou.yike.utils.ActivityCollector;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public abstract class BaseActivity extends AutoLayoutActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeWindow(savedInstanceState);
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        init(savedInstanceState);
        setListener();

    }

    /***
     * 用于在初始化View之前做一些事
     */
    protected void beforeWindow(Bundle savedInstanceState) {
        /**
         * 全局 activity 容器 添加activity
         */
        ActivityCollector.addActivity(this);
    }

    protected abstract int setLayoutResourceID();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setListener();

    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        /**
         * 从全局中移除
         */
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
