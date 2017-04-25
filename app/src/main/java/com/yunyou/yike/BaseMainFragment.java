package com.yunyou.yike;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public abstract class BaseMainFragment extends Fragment implements IView {
    protected IPrenester mIPrenester;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIPrenester = setIPrenester();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLayout = getViewLayout(inflater, container, savedInstanceState);
        initView(viewLayout, savedInstanceState);
        setlistener();
        startRefresh(null);
        return viewLayout;
    }


    protected abstract View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract IPrenester setIPrenester();

    protected abstract void initView(View viewLayout, Bundle savedInstanceState);

    protected abstract void setlistener();

}
