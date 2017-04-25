package com.yunyou.yike.order;

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
public class OrderFragment extends BaseMainFragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    /**
     * 构造方法
     */
    public OrderFragment() {
    }

    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static OrderFragment newInstance(String param1) {
        OrderFragment fragment = new OrderFragment();
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
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {

    }


    @Override
    public void startRefresh(Object object) {

    }
}
