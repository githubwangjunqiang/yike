package com.yunyou.yike;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.yunyou.yike.Interface.IMainFragmentListener;
import com.yunyou.yike.home.HomeFragment;
import com.yunyou.yike.msg.MessageFragment;
import com.yunyou.yike.my.MyFragment;
import com.yunyou.yike.order.OrderFragment;

public class MainActivity extends BaseActivity implements IMainFragmentListener {
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;
    private int index;//碎片下标
    private Fragment[] fragments = new Fragment[4];
    private FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance("");
            fragmentTransaction.add(R.id.content_main, homeFragment, "HomeFragment");
            orderFragment = OrderFragment.newInstance("");
            fragmentTransaction.add(R.id.content_main, orderFragment, "OrderFragment");
            messageFragment = MessageFragment.newInstance("");
            fragmentTransaction.add(R.id.content_main, messageFragment, "MessageFragment");
            myFragment = MyFragment.newInstance("");
            fragmentTransaction.add(R.id.content_main, myFragment, "MyFragment");
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            orderFragment = (OrderFragment) getSupportFragmentManager().findFragmentByTag("OrderFragment");
            messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentByTag("MessageFragment");
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("MyFragment");
        }


        fragments[0] = homeFragment;
        fragments[1] = orderFragment;
        fragments[2] = messageFragment;
        fragments[3] = myFragment;
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
        supportFragmentManager.putFragment(outState, "MessageFragment", messageFragment);
        supportFragmentManager.putFragment(outState, "MyFragment", myFragment);
    }

    @Override
    protected void setListener() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home://点击首页
                    showHideFragment(0);
                    return true;
                case R.id.navigation_dashboard://点击订单
                    showHideFragment(1);
                    return true;
                case R.id.navigation_msg://点击消息
                    showHideFragment(2);
                    return true;
                case R.id.navigation_notifications://点击我的
                    showHideFragment(3);
                    return true;
            }
            return false;
        }

    };

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
    public void callBack(int position) {//四个主碎片的监听回调

    }
}
