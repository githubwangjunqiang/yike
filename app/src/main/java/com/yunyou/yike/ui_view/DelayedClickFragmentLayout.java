package com.yunyou.yike.ui_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.yunyou.yike.utils.LogUtils;
import com.zhy.autolayout.AutoFrameLayout;

/**
 * Created by ${王俊强} on 2017/6/2.
 */

public class DelayedClickFragmentLayout extends AutoFrameLayout {
    private long time;

    public void setTime(long time) {
        this.time = time;
    }

    public DelayedClickFragmentLayout(Context context) {
        super(context);
        setTime(600L);
    }

    public DelayedClickFragmentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTime(600L);
    }

    public DelayedClickFragmentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTime(600L);
    }

    public DelayedClickFragmentLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTime(600L);
    }

    @Override
    public boolean performClick() {
        setClickable(false);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    setClickable(true);
                } catch (Exception e) {
                    LogUtils.e("连续点击按钮触发异常");
                }
            }
        }, time);
        return super.performClick();
    }
}
