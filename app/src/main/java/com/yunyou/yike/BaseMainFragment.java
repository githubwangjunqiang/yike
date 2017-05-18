package com.yunyou.yike;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.Interface_view.IView;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public abstract class BaseMainFragment extends Fragment implements IView {
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = getViewLayout(inflater, container, savedInstanceState);
        initView(mRootView, savedInstanceState);
        setlistener();
        startRefresh(null);
        return mRootView;
    }

    protected <T extends View> T obtainView(int resID) {
        return (T) mRootView.findViewById(resID);
    }


    protected abstract View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    protected abstract void initView(View viewLayout, Bundle savedInstanceState);

    protected abstract void setlistener();

}
