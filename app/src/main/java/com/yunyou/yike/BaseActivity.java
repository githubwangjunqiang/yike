package com.yunyou.yike;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.fingdo.statelayout.StateLayout;
import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.ui_view.dialog.LoadingDialog;
import com.yunyou.yike.ui_view.pulllayout.FootView;
import com.yunyou.yike.ui_view.pulllayout.HeadView;
import com.yunyou.yike.utils.ActivityCollector;
import com.yunyou.yike.utils.StatusBarCompat;
import com.yunyou.yike.utils.To;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public abstract class BaseActivity extends AutoLayoutActivity implements IView {
    private LoadingDialog mLoadingDialog;
    private StateLayout mStateLayout;
    protected int statusBarColor = 0;//状态栏颜色
    private View statusBarView = null;//
    private PtrFrameLayout mRefreshLayout;
    protected PullRefreshLayout mPullRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean booleanss = beforeWindow(savedInstanceState);
        if (booleanss) {
            finish();
            return;
        }
        setContentCiew();
        setPresenter();
        initDialog();
        init(savedInstanceState);
        setListener();
    }

    protected void setPresenter() {

    }

    /**
     * 设置
     */
    private void setContentCiew() {
        ActivityCollector.addActivity(this);
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
        if (getStateLayoutID() != 0) {
            mStateLayout = optionView(getStateLayoutID());
        }
        if (mStateLayout != null) {
            mStateLayout.setUseAnimation(true);
            mStateLayout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    startRefresh(true);
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

                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                        return super.checkCanDoRefresh(frame, content, header);
                    }
                });
            }
        }
        if (getRecyerViewLayoutID() != 0) {
            mPullRefreshLayout = optionView(getRecyerViewLayoutID());
            if (mPullRefreshLayout != null) {
                initRecyerViewLayout();
            }
        }

    }

    /**
     * 设置recyerview layout 刷新布局参数
     */
    protected void initRecyerViewLayout() {
        mPullRefreshLayout.setHeaderView(new HeadView(this));
        mPullRefreshLayout.setFooterView(new FootView(this));
        mPullRefreshLayout.setRefreshEnable(true);
        mPullRefreshLayout.setLoadMoreEnable(true);
    }

    /**
     * 是否使用列表刷新布局
     *
     * @return
     */
    protected int getRecyerViewLayoutID() {
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
     * 关闭加载对话框
     */
    protected void hideDialog() {
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
    protected boolean beforeWindow(Bundle savedInstanceState) {

        return false;
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
    public void showLoginView(Object object) {
        hideDialog();
        if (mStateLayout != null) {
            if (object != null) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        hideDialog();
        if (!TextUtils.isEmpty(string)) {
            To.oo(string);
        }
    }
}
