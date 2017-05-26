package com.yunyou.yike.fragment.msg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.R;

public class FriendsFragment extends BaseMainFragment {

    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }


    @Override
    public void startRefresh(Object object) {

    }

    @Override
    public void showLoodingView(Object object) {


    }

    @Override
    public void showLoodingDialog(Object object) {

    }

    @Override
    public void showContentView(Object object) {

    }

    @Override
    public void showErrorView(Object object) {

    }

    @Override
    public void showEmptyView(Object object) {

    }

    @Override
    public void showNoNetworkView(Object object) {

    }

    @Override
    public void showTimeErrorView(Object object) {

    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feel, container, false);
    }


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {

    }

    @Override
    protected void setlistener() {

    }
}
