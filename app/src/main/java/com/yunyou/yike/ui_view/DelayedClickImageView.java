package com.yunyou.yike.ui_view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class DelayedClickImageView extends android.support.v7.widget.AppCompatImageView {
    private long time;

    public void setTime(long time) {
        this.time = time;
    }

    public DelayedClickImageView(Context context) {
        super(context);
    }

    public DelayedClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayedClickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    setClickable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, time);
        return super.

                performClick();
    }
}
