package com.yunyou.yike;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.ui_view.dialog.LoadingDialog;
import com.yunyou.yike.utils.ActivityCollector;
import com.yunyou.yike.utils.To;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public abstract class BaseActivity extends AutoLayoutActivity implements IView {
    protected LoadingDialog mLoadingDialog;
    protected StateLayout mStateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeWindow(savedInstanceState);
        if (setLayoutResourceID() != 0) {
            setContentView(setLayoutResourceID());
        }
        setPresenter();
        init(savedInstanceState);
        setListener();
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
        mStateLayout = (StateLayout) findViewById(getStateLayoutID());
        if (mStateLayout != null) {
            mStateLayout.setUseAnimation(true);
        }
    }

    protected abstract int setLayoutResourceID();

    protected abstract int getStateLayoutID();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setListener();

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
    protected void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
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
        /**
         * 从全局中移除
         */
        ActivityCollector.removeActivity(this);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
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
        showDialog();
    }

    @Override
    public void showContentView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            mStateLayout.showContentView();
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
    }
}
