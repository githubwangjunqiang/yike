package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.http.entity.RxApi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {
    protected RxApi mApi;
    protected Gson mGson;

    public BasePresenter(RxApi mApi, Gson mGson) {
        this.mApi = mApi;
        this.mGson = mGson;
    }

    protected Reference<T> mTReference;//view 接口类型弱引用

    public void attachView(T view) {
        mTReference = new WeakReference<T>(view);//建立关联
    }

    protected T getView() {
        if(mTReference == null){
            return null;
        }
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
