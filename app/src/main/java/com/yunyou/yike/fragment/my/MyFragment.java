package com.yunyou.yike.fragment.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.BaseMVPFragment;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.RongIM.RongIMLoginManager;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.activity.MyYueActivity;
import com.yunyou.yike.activity.XinxiGuanliActivity;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.MyFragmentPresenter;
import com.yunyou.yike.utils.ActivityCollector;


/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class MyFragment extends BaseMVPFragment<IView.IMyFragmentView, MyFragmentPresenter>
        implements IView.IMyFragmentView {

    private Button mButtonLoginOut;
    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
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
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    private PullRefreshLayout mRefreshLayout;
    private SimpleDraweeView mDraweeView;
    private TextView mTextViewDataInfo,//信息管理
            mTextViewQianbao;//我的钱包

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mRefreshLayout = obtainView(R.id.my_layout);
        mDraweeView = obtainView(R.id.my_image);
        mButtonLoginOut = obtainView(R.id.my_btn_loginout);
        mTextViewDataInfo = obtainView(R.id.my_tvxinxiguanli);
        mTextViewQianbao = obtainView(R.id.my_qianbao);
    }

    @Override
    protected void setlistener() {
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mTextViewDataInfo.setOnClickListener(new View.OnClickListener() {//信息管理
            @Override
            public void onClick(View v) {//信息管理
                startActivity(new Intent(getContext(), XinxiGuanliActivity.class));
            }
        });
        mTextViewQianbao.setOnClickListener(new View.OnClickListener() {//我的钱包
            @Override
            public void onClick(View v) {//我的钱包界面（我的余额我的银行卡）
                startActivity(new Intent(getContext(), MyYueActivity.class));
            }
        });
        mButtonLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击退出登录
                RongIMLoginManager.getInstance().disConnectRongIM(true);
                ActivityCollector.finishAll();
                LoginActivity.startLoginActivity(getContext(),true);
            }
        });
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        mPresenter.getUserInfo();
    }


    @Override
    protected void RogerMessage(EventBusMessage message) {

    }


    @Override
    public void showUserInfo() {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse("http://img.yxbao.com/news/image/201501/16/586a693557.gif"))
                .setAutoPlayAnimations(true)
                .build();
        mDraweeView.setController(controller);
    }

    @Override
    protected MyFragmentPresenter mPresenterCreate() {
        return new MyFragmentPresenter(null,null);
    }
}
