package com.yunyou.yike.msg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.R;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class MessageFragment extends BaseMainFragment {



    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static MessageFragment newInstance(String param1) {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }


    @Override
    protected IPrenester setIPrenester() {
        return null;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {

    }

    @Override
    protected void setlistener() {

    }


    @Override
    public void startRefresh(Object object) {

    }
}
