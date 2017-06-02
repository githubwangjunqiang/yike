package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.fragment.home.HomeFragment;
import com.yunyou.yike.fragment.msg.MessageFragment;
import com.yunyou.yike.fragment.my.MyFragment;
import com.yunyou.yike.fragment.myorder.MyOrderFragment;
import com.yunyou.yike.fragment.order.OrderFragment;


public class MainActivity extends BaseActivity {
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private MyOrderFragment mOrderFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;
    private int index;//碎片下标
    private Fragment[] fragments = new Fragment[5];
    private FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        supportFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            supportFragmentManager.beginTransaction().add(R.id.content_main, homeFragment, "HomeFragment").commit();
            orderFragment = OrderFragment.newInstance();
            supportFragmentManager.beginTransaction().add(R.id.content_main, orderFragment, "OrderFragment").commit();
            mOrderFragment = MyOrderFragment.newInstance();
            supportFragmentManager.beginTransaction().add(R.id.content_main, mOrderFragment, "mOrderFragment").commit();
            messageFragment = MessageFragment.newInstance("");
            supportFragmentManager.beginTransaction().add(R.id.content_main, messageFragment, "MessageFragment").commit();
            myFragment = MyFragment.newInstance("");
            supportFragmentManager.beginTransaction().add(R.id.content_main, myFragment, "MyFragment").commit();
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentByTag("OrderFragment");
            mOrderFragment = (MyOrderFragment) getSupportFragmentManager().findFragmentByTag("mOrderFragment");
            messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentByTag("MessageFragment");
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("MyFragment");
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragments[0] = homeFragment;
        fragments[1] = orderFragment;
        fragments[2] = mOrderFragment;
        fragments[3] = messageFragment;
        fragments[4] = myFragment;
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.show(fragments[index]);
        fragmentTransaction.commit();
        index = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.putFragment(outState, "HomeFragment", homeFragment);
        supportFragmentManager.putFragment(outState, "OrderFragment", orderFragment);
        supportFragmentManager.putFragment(outState, "mOrderFragment", mOrderFragment);
        supportFragmentManager.putFragment(outState, "MessageFragment", messageFragment);
        supportFragmentManager.putFragment(outState, "MyFragment", myFragment);
    }

    @Override
    protected void setListener() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home://点击首页
                        showHideFragment(0);
                        return true;
                    case R.id.navigation_dashboard://点击订单
                        showHideFragment(1);
                        return true;
                    case R.id.navigation_wodedingdan://点击我的接单
                        showHideFragment(2);
                        return true;
                    case R.id.navigation_msg://点击消息
                        showHideFragment(3);
                        return true;
                    case R.id.navigation_notifications://点击我的
                        showHideFragment(4);
                        return true;
                }
                return false;
            }

        };
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    /**
     * 显示隐藏碎片
     *
     * @param index
     */
    private void showHideFragment(int index) {
        if (this.index == index) {
            return;
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        if (this.index < index) {
            fragmentTransaction.setCustomAnimations(R.anim.fragment_translate_left_in,
                    R.anim.fragment_translate_left_out);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.fragment_translate_right_in,
                    R.anim.fragment_translate_right_out);
        }
        fragmentTransaction.hide(fragments[this.index]);
        fragmentTransaction.show(fragments[index]);
        fragmentTransaction.commit();
        this.index = index;

    }

    @Override
    public void startRefresh(Object object) {

    }

    @Override
    protected void onDestroy() {
        App.appOut();
        System.exit(0);
        super.onDestroy();
    }
}
