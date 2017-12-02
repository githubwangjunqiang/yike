package com.yunyou.yike.ui_view;

import android.content.Context;
import android.util.AttributeSet;

import com.fingdo.statelayout.StateLayout;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by ${王俊强} on 2017/6/20.
 */

public class MyStateLayout extends StateLayout {
    public MyStateLayout(Context context) {
        super(context);
    }

    public MyStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);


    @Override
    public AutoFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
