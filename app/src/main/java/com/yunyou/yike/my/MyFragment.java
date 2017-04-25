package com.yunyou.yike.my;

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
public class MyFragment extends BaseMainFragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    /**
     * 构造方法
     */
    public MyFragment() {
    }

    /**
     * 传参构造方法
     * @param param1
     * @return
     */
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected IPrenester setIPrenester() {
        return null;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {

    }

    @Override
    public void startRefresh(Object object) {

    }
}
