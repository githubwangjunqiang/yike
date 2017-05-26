package com.yunyou.yike.fragment.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.R;
import com.yunyou.yike.fragment.order.fragment.AllOrderFragment;
import com.yunyou.yike.fragment.order.fragment.CancelOrderFragment;
import com.yunyou.yike.fragment.order.fragment.FinishedOrderFragment;
import com.yunyou.yike.fragment.order.fragment.UnfinishedOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class OrderFragment extends BaseMainFragment {

    /**
     * 传参构造方法
     *
     * @return
     */
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }



    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private AllOrderFragment mAllOrderFragment;
    private UnfinishedOrderFragment mUnfinishedOrderFragment;
    private FinishedOrderFragment mFinishedOrderFragment;
    private CancelOrderFragment mCancelOrderFragment;
    private List<String> mStrings;
    private List<Fragment> mFragments;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        getChildFragmentManager().putFragment(outState,"mAllOrderFragment",mAllOrderFragment);
//        getChildFragmentManager().putFragment(outState,"mUnfinishedOrderFragment",mUnfinishedOrderFragment);
//        getChildFragmentManager().putFragment(outState,"mFinishedOrderFragment",mFinishedOrderFragment);
//        getChildFragmentManager().putFragment(outState,"mCancelOrderFragment",mCancelOrderFragment);
    }

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mTabLayout = (TabLayout) viewLayout.findViewById(R.id.order_tablayout);
        mViewPager = (ViewPager) viewLayout.findViewById(R.id.order_viewpager);
//        if (savedInstanceState != null) {
//            FragmentManager manager = getChildFragmentManager();
//            mAllOrderFragment = (AllOrderFragment) manager.getFragment(savedInstanceState,
//                    "mAllOrderFragment");
//            manager.beginTransaction().add(mAllOrderFragment, "mAllOrderFragment").commit();
//            mUnfinishedOrderFragment = (UnfinishedOrderFragment) manager.getFragment(savedInstanceState,
//                    "mUnfinishedOrderFragment");
//            manager.beginTransaction().add(mUnfinishedOrderFragment, "mUnfinishedOrderFragment").commit();
//            mFinishedOrderFragment = (FinishedOrderFragment) manager.getFragment(savedInstanceState,
//                    "mFinishedOrderFragment");
//            manager.beginTransaction().add(mFinishedOrderFragment, "mFinishedOrderFragment").commit();
//            mCancelOrderFragment = (CancelOrderFragment) manager.getFragment(savedInstanceState,
//                    "mCancelOrderFragment");
//            manager.beginTransaction().add(mCancelOrderFragment, "mCancelOrderFragment").commit();
//        } else {
//            mAllOrderFragment = AllOrderFragment.newInstance();
//            mUnfinishedOrderFragment = UnfinishedOrderFragment.newInstance();
//            mFinishedOrderFragment = FinishedOrderFragment.newInstance();
//            mCancelOrderFragment = CancelOrderFragment.newInstance();
//        }
        mAllOrderFragment = AllOrderFragment.newInstance();
        mUnfinishedOrderFragment = UnfinishedOrderFragment.newInstance();
        mFinishedOrderFragment = FinishedOrderFragment.newInstance();
        mCancelOrderFragment = CancelOrderFragment.newInstance();
        mFragments = new ArrayList<>();
        mFragments.add(mAllOrderFragment);
        mFragments.add(mUnfinishedOrderFragment);
        mFragments.add(mFinishedOrderFragment);
        mFragments.add(mCancelOrderFragment);
        mStrings = new ArrayList<>();
        mStrings.add("全部订单");
        mStrings.add("未完订单");
        mStrings.add("已完订单");
        mStrings.add("取消订单");
    }

    @Override
    protected void setlistener() {

    }


    @Override
    public void startRefresh(Object object) {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mStrings.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
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
}
