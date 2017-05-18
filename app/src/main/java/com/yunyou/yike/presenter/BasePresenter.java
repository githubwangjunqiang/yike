package com.yunyou.yike.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {

    protected Reference<T> mTReference;//view 接口类型弱引用

    public void attachView(T view) {
        mTReference = new WeakReference<T>(view);//建立关联
    }

    protected T getView() {
        return mTReference.get();
    }

    public boolean isViewAttached() {
        return mTReference != null && mTReference.get() != null;
    }

    public void detachView() {
        if (mTReference != null) {
            mTReference.clear();
            mTReference = null;
        }
    }

    
}
