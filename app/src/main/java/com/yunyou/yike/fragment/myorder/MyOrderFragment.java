package com.yunyou.yike.fragment.myorder;

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
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.fragment.order.fragment.AllOrderFragment;
import com.yunyou.yike.fragment.order.fragment.CancelOrderFragment;
import com.yunyou.yike.fragment.order.fragment.FinishedOrderFragment;
import com.yunyou.yike.fragment.order.fragment.UnfinishedOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class MyOrderFragment extends BaseMainFragment {

    /**
     * 传参构造方法
     *
     * @return
     */
    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
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
        mAllOrderFragment = AllOrderFragment.newInstance(true);
        mUnfinishedOrderFragment = UnfinishedOrderFragment.newInstance(true);
        mFinishedOrderFragment = FinishedOrderFragment.newInstance(true);
        mCancelOrderFragment = CancelOrderFragment.newInstance(true);
        mFragments = new ArrayList<>();
        mFragments.add(mAllOrderFragment);
        mFragments.add(mUnfinishedOrderFragment);
        mFragments.add(mFinishedOrderFragment);
        mFragments.add(mCancelOrderFragment);
        mStrings = new ArrayList<>();
        mStrings.add(getString(R.string.quanbudingdan));
        mStrings.add(getString(R.string.weiwandingdan));
        mStrings.add(getString(R.string.yiwandingdan));
        mStrings.add(getString(R.string.overdingdan));
    }

    @Override
    protected void setlistener() {

    }


    @Override
    public void startRefresh(boolean isShowLoadingView) {
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
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mStrings.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    protected void RogerMessage(EventBusMessage message) {

    }
}
