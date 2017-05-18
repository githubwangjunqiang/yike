package com.yunyou.yike.fragment.msg;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.R;
import com.yunyou.yike.fragment.msg.fragment.FeelFragment;
import com.yunyou.yike.fragment.msg.fragment.FriendsFragment;
import com.yunyou.yike.fragment.msg.fragment.MsgFragment;

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
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mIvEditing;
    private Fragment[] mFragments;
    private MsgFragment mMsgFragment;
    private FriendsFragment mFriendsFragment;
    private FeelFragment mFeelFragment;
    private String[] mStrings;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mIvEditing = (ImageView) viewLayout.findViewById(R.id.message_ivediting);
        mViewPager = (ViewPager) viewLayout.findViewById(R.id.message_vp);
        mTabLayout = (TabLayout) viewLayout.findViewById(R.id.message_tablayout);
        mMsgFragment = MsgFragment.newInstance();
        mFriendsFragment = FriendsFragment.newInstance();
        mFeelFragment = FeelFragment.newInstance();
        mFragments = new Fragment[]{mMsgFragment, mFriendsFragment, mFeelFragment};
        mStrings = new String[]{getString(R.string.msg), getString(R.string.haoyou),
                getString(R.string.ganxiang)};
    }

    @Override
    protected void setlistener() {

    }


    @Override
    public void startRefresh(Object object) {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mStrings[position];
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void showLoodingView(Object object) {

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
