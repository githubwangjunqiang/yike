package com.yunyou.yike.fragment.msg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;

public class MsgFragment extends BaseMainFragment {

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }


    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
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

    @Override
    protected void RogerMessage(EventBusMessage message) {

    }

    @Override
    public void startRefresh(Object object) {

    }
}
