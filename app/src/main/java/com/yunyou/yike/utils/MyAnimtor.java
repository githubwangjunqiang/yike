package com.yunyou.yike.utils;

import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by wangjunqiang on 2016/10/24.
 */
public class MyAnimtor {

    /**
     * 抖动
     *
     * @param views
     * @return
     */
    public static ObjectAnimator getAnimator_DX(View views) {
        Reference<View> viewReference = new WeakReference<View>(views);
        float yy = viewReference.get().getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(views,
                "translationY", yy, -20f, 20f, -10f, 10f, yy);
        animator.setDuration(400);
        return animator;


    }
}
