package com.yunyou.yike;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.baoyz.widget.PullRefreshLayout;
import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.ui_view.dialog.LoadingDialog;
import com.yunyou.yike.utils.ActivityCollector;
import com.yunyou.yike.utils.StatusBarCompat;
import com.yunyou.yike.utils.To;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public abstract class BaseActivity extends AutoLayoutActivity implements IView {
    protected LoadingDialog mLoadingDialog;
    protected StateLayout mStateLayout;
    protected int statusBarColor = 0;//状态栏颜色
    protected View statusBarView = null;//
    protected PullRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeWindow(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (setLayoutResourceID() != 0) {
            setContentView(setLayoutResourceID());
        }
        if (statusBarColor == 0) {
            statusBarView = StatusBarCompat.compat(this,
                    ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else if (statusBarColor != -1) {
            statusBarView = StatusBarCompat.compat(this, statusBarColor);
        }


        setPresenter();
        initDialog();
        init(savedInstanceState);
        setListener();
    }

    /**
     * 初始化 加载对话框
     */
    private void initDialog() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setListener(new LoadingDialog.LoadingListener() {
            @Override
            public void showListener() {
                showLoadingListener();
            }

            @Override
            public void dismissListener() {
                hideLoadingListener();
            }
        });
        mStateLayout = optionView(getStateLayoutID());
        if (mStateLayout != null) {
            mStateLayout.setUseAnimation(true);
            mStateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    startRefresh(null);
                }

                @Override
                public void loginClick() {
                    LoginActivity.startLoginActivity(BaseActivity.this, true);
                }
            });
        }
        if (getPullRefreshLayoutID() != 0) {
            mRefreshLayout = optionView(getPullRefreshLayoutID());
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
                mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        startRefresh(null);
                    }
                });
            }
        }

    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage message) {
        rogerMessage(message);
    }

    protected void transparent19and20() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    protected abstract int setLayoutResourceID();

    protected abstract int getStateLayoutID();

    protected abstract int getPullRefreshLayoutID();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setListener();

    protected abstract void rogerMessage(EventBusMessage message);

    /**
     * 对话框显示了
     */
    protected void showLoadingListener() {

    }

    /**
     * 对话框被用户消失了
     */
    protected void hideLoadingListener() {
        finish();
    }

    /**
     * 显示加载对话框
     */
    protected void showDialog(String msage) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            if (TextUtils.isEmpty(msage)) {
                mLoadingDialog.setText("Loading......");
            } else {
                mLoadingDialog.setText(msage);
            }
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideDiaLogView() {
        hideDialog();
    }

    /**
     * 显示加载对话框
     */
    protected void hideDialog() {
        if (mLoadingDialog == null) {
            return;
        }
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    /**
     * 创建presenter
     */
    protected void setPresenter() {

    }

    /**
     * 隐藏软键盘
     */
    protected void hidekeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 初始化view
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T optionView(int id) {
        return (T) super.findViewById(id);
    }

    /***
     * 用于在初始化View之前做一些事
     */
    protected void beforeWindow(Bundle savedInstanceState) {
        /**
         * 全局 activity 容器 添加activity
         */
        ActivityCollector.addActivity(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        /**
         * 从全局中移除
         */
        ActivityCollector.removeActivity(this);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
        System.gc();
        super.onDestroy();
    }


    @Override
    public void showLoodingView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showLoadingView((String) object);
            } else {
                mStateLayout.showLoadingView();
            }
        }
    }

    @Override
    public void showLoodingDialog(Object object) {
        showDialog((String) object);
    }

    @Override
    public void showContentView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            mStateLayout.showContentView();
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showErrorView((String) object);
            } else {
                mStateLayout.showErrorView();
            }
        } else {
            To.ee(object);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showEmptyView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showEmptyView((String) object);
            } else {
                mStateLayout.showEmptyView();
            }
        } else {
            To.ee(object);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showLoginView(Object object) {
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showLoginView((String) object);
            } else {
                mStateLayout.showLoginView();
            }
        } else {
            To.ee(object);
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNoNetworkView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showNoNetworkView((String) object);
            } else {
                mStateLayout.showNoNetworkView();
            }
        } else {
            To.ee(object);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showTimeErrorView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
                mStateLayout.showTimeoutView((String) object);
            } else {
                mStateLayout.showTimeoutView();
            }
        } else {
            To.ee(object);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(statusBarColor);
        }
    }

    @Override
    public void ToToast(String string) {
        if (!TextUtils.isEmpty(string)) {
            To.oo(string);
        }
    }
}
