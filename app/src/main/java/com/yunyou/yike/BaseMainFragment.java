package com.yunyou.yike;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingdo.statelayout.StateLayout;
import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.ui_view.dialog.LoadingDialog;
import com.yunyou.yike.ui_view.pulllayout.FootView;
import com.yunyou.yike.ui_view.pulllayout.HeadView;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public abstract class BaseMainFragment extends Fragment implements IView {
    private View mRootView;
    protected StateLayout mStateLayout;
    protected LoadingDialog mLoadingDialog;
    protected PtrFrameLayout mRefreshLayout;
    protected PullRefreshLayout mPullRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            return mRootView;
        }


        mRootView = getViewLayout(inflater, container, savedInstanceState);


        initView(mRootView, savedInstanceState);
        initLayout();
        setlistener();
        startRefresh(true);


        return mRootView;
    }

    private void initLayout() {
        if (getStateLayoutID() != 0) {
            mStateLayout = optainView(getStateLayoutID());
            if (mStateLayout != null) {
                mStateLayout.setUseAnimation(true);
                mStateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                    @Override
                    public void refreshClick() {
                        startRefresh(true);
                    }

                    @Override
                    public void loginClick() {
                        LoginActivity.startLoginActivity(getContext(), true);
                    }
                });
            }
        }
        if (getPullRefreshLayoutID() != 0) {
            mRefreshLayout = optainView(getPullRefreshLayoutID());
            if (mRefreshLayout != null) {
                mRefreshLayout.setResistance(1.7F);//阻力
                mRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2F);//标头的高度与触发刷新的比例
                mRefreshLayout.setDurationToClose(300);//从您将视图相对位置移动到标题高度的持续时间为默认值
                mRefreshLayout.setDurationToCloseHeader(1000);//关闭标题的持续时间
                mRefreshLayout.setKeepHeaderWhenRefresh(true);//在刷新时保持标题
                mRefreshLayout.setPullToRefresh(false);//拉动就刷新还是释放后刷新
                mRefreshLayout.setLoadingMinTime(500);//最小等待时间|
                mRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
                    @Override
                    public void onRefreshBegin(PtrFrameLayout frame) {
                        startRefresh(false);
                    }
                });
            }
        }

        mLoadingDialog = new LoadingDialog(getContext());
        mLoadingDialog.setListener(new LoadingDialog.LoadingListener() {
            @Override
            public void showListener() {

            }

            @Override
            public void dismissListener() {

            }
        });
        if (getRecyerViewID() != 0) {
            mPullRefreshLayout = optainView(getRecyerViewID());
            if (mPullRefreshLayout != null) {
                ititRecyerView();
            }
        }

    }

    /**
     * 初始化recyerView 刷新布局
     */
    protected void ititRecyerView() {
        mPullRefreshLayout.setHeaderView(new HeadView(this.getContext()));
        mPullRefreshLayout.setFooterView(new FootView(this.getContext()));
        mPullRefreshLayout.setRefreshEnable(true);
        mPullRefreshLayout.setLoadMoreEnable(true);
    }

    /**
     * 刷新recyerview
     *
     * @return
     */
    protected int getRecyerViewID() {
        return 0;
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage message) {
        if (message != null && message.getMsgCode() == EventBusMessage.RONGIMTOKENINCORRECT) {//融云失效
            To.ee(getString(R.string.rongim_incorrect));
        }
        if (message != null && message.getMsgCode() == EventBusMessage.TOKENLOGIN) {
            startRefresh(true);
        }

        RogerMessage(message);
    }


    protected abstract int getStateLayoutID();

    protected abstract int getPullRefreshLayoutID();

    protected <T extends View> T optainView(int resID) {
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
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.refreshComplete();
        }
        if (mPullRefreshLayout != null) {
            mPullRefreshLayout.refreshComplete();
            mPullRefreshLayout.loadMoreComplete();
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
        if (mStateLayout != null) {
            mStateLayout.showContentView();
        }
    }

    @Override
    public void showErrorView(Object object) {
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
        } else {
            showLoginDiaLog();
        }

    }

    /**
     * 显示登陆对话框
     */
    private void showLoginDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您的登陆已过期，为了您的安全，请您重新登陆");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                LoginActivity.startLoginActivity(getContext(), true);
            }
        });
        builder.show();
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
        hideDialogLoading();
        if (!TextUtils.isEmpty(string)) {
            To.oo(string);
        }
    }
}
