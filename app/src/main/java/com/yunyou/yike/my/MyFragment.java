package com.yunyou.yike.my;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.R;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class MyFragment extends BaseMainFragment {


    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    protected IPrenester setIPrenester() {
        return null;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    private PullRefreshLayout mRefreshLayout;
    private SimpleDraweeView mDraweeView;

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mRefreshLayout = (PullRefreshLayout) viewLayout.findViewById(R.id.my_layout);
        mDraweeView = (SimpleDraweeView) viewLayout.findViewById(R.id.my_image);
    }

    @Override
    protected void setlistener() {
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void startRefresh(Object object) {
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(Uri.parse("http://img2.3lian.com/2014/f2/37/d/34.jpg"))
//                .setAutoPlayAnimations(true)
//                .build();
//        mDraweeView.setController(controller);
        mDraweeView.setImageURI(Uri.parse("http://img2.3lian.com/2014/f2/37/d/34.jpg"));
    }
}
