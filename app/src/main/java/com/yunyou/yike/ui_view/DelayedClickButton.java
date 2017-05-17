package com.yunyou.yike.ui_view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

/**
 * Created by ${王俊强} on 2017/4/11.
 */

public class DelayedClickButton extends android.support.v7.widget.AppCompatButton {
    public DelayedClickButton(Context context) {
        super(context);
    }

    public DelayedClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayedClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    setClickable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
        return super.performClick();
    }
}
