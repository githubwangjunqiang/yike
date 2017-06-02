package com.yunyou.yike;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.ui_view.dialog.LoadingDialog;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public abstract class BaseMainFragment extends Fragment implements IView {
    private View mRootView;
    protected StateLayout mStateLayout;
    protected LoadingDialog mLoadingDialog;
    protected PullRefreshLayout mRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = getViewLayout(inflater, container, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initView(mRootView, savedInstanceState);
        if (getStateLayoutID() != 0) {
            mStateLayout = obtainView(getStateLayoutID());
            if (mStateLayout != null) {
                mStateLayout.setUseAnimation(true);
                mStateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                    @Override
                    public void refreshClick() {
                        startRefresh(null);
                    }

                    @Override
                    public void loginClick() {
                        LoginActivity.startLoginActivity(getContext(), true);
                    }
                });
            }
        }
        if (getPullRefreshLayoutID() != 0) {
            mRefreshLayout = obtainView(getPullRefreshLayoutID());
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


        mLoadingDialog = new LoadingDialog(getContext());
        setlistener();
        mLoadingDialog.setListener(new LoadingDialog.LoadingListener() {
            @Override
            public void showListener() {

            }

            @Override
            public void dismissListener() {

            }
        });
        startRefresh(null);
        return mRootView;
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage message) {
        RogerMessage(message);
    }


    protected abstract int getStateLayoutID();

    protected abstract int getPullRefreshLayoutID();

    protected <T extends View> T obtainView(int resID) {
        return (T) mRootView.findViewById(resID);
    }


    protected abstract View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    protected abstract void initView(View viewLayout, Bundle savedInstanceState);

    protected abstract void setlistener();

    protected abstract void RogerMessage(EventBusMessage message);

    /**
     * 显示对话框
     *
     * @param object
     */
    protected void showDialogLoading(Object object) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
            if (object != null) {
                mLoadingDialog.setText((String) object);
            }
        }
    }

    /**
     * 关闭对话框
     */
    protected void hideDialogLoading() {
        if (mLoadingDialog == null) {
            return;
        }
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hideDiaLogView() {
        hideDialogLoading();
    }

    @Override
    public void showLoodingView(Object object) {
        hideDialogLoading();

        if (mStateLayout != null) {
            mStateLayout.showLoadingView();
        }
    }

    @Override
    public void showLoodingDialog(Object object) {
        showDialogLoading(object);
    }

    @Override
    public void showContentView(Object object) {
        hideDialogLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
        if (mStateLayout != null) {
            mStateLayout.showContentView();
        }
    }

    @Override
    public void showErrorView(Object object) {
        To.ee(object == null ? "获取失败" : object);
        hideDialogLoading();
        if (mStateLayout != null) {
            if (object == null) {
                mStateLayout.showErrorView((String) object);
            } else {
                mStateLayout.showErrorView();
            }
        }
    }

    @Override
    public void showLoginView(Object object) {
        hideDialogLoading();
        if (mStateLayout != null) {
            if (object == null) {
                mStateLayout.showLoginView((String) object);
            } else {
                mStateLayout.showLoginView();
            }
        }
    }

    @Override
    public void showEmptyView(Object object) {
        To.ee(object == null ? "没有数据" : object);
        hideDialogLoading();
        if (mStateLayout != null) {
            if (object == null) {
                mStateLayout.showEmptyView((String) object);
            } else {
                mStateLayout.showEmptyView();
            }
        }
    }

    @Override
    public void showNoNetworkView(Object object) {
        To.ee(object == null ? "没有网络链接" : object);
        hideDialogLoading();
        if (mStateLayout != null) {
            if (object == null) {
                mStateLayout.showNoNetworkView((String) object);
            } else {
                mStateLayout.showNoNetworkView();
            }
        }
    }

    @Override
    public void showTimeErrorView(Object object) {
        To.ee(object == null ? "链接超时啦" : object);
        hideDialogLoading();
        if (mStateLayout != null) {
            if (object == null) {
                mStateLayout.showTimeoutView((String) object);
            } else {
                mStateLayout.showTimeoutView();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void ToToast(String string) {
        if (!TextUtils.isEmpty(string)) {
            To.oo(string);
        }
    }
}
