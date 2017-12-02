package com.yunyou.yike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.MyAppInfoAdapter;
import com.yunyou.yike.dagger2.DaggerMyAppInfoCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.MyappInfo;
import com.yunyou.yike.presenter.MyAppInfoActivityPresenter;
import com.yunyou.yike.ui_view.pulllayout.FootView;
import com.yunyou.yike.ui_view.pulllayout.HeadView;

import java.util.Map;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class MyAppInfoActivity extends BaseMVPActivity<IView.IMyAppInfoActivityView,
        MyAppInfoActivityPresenter> implements IView.IMyAppInfoActivityView {
    @Inject
    MyAppInfoActivityPresenter mMyAppInfoActivityPresenter;


//    private SWPullRecyclerLayout mSWPullRecyclerLayout;

    private RecyclerView mRecyclerView;
    private PullRefreshLayout mPullRefreshLayout;
    private TextView mTextViewTitle;
    private MyAppInfoAdapter adapter;
    private ImageView mImageViewBack;
    public static final String MYTYPE1 = "1";
    public static final String MYTYPE2 = "2";
    public static final String MYTYPE3 = "3";
    public static final String MYTYPE4 = "4";
    public static final String MYTYPE5 = "5";
    public static final String MYTYPE = "type";

    /**
     * 启动activity
     *
     * @param context 上下文
     * @param type    文章类型值    1为最新公告  2服务协议   3平台奖惩   4学习园地   5评分说明
     */
    public static void startMyAppInfoActivity(Context context, String type) {
        Intent intent = new Intent(context, MyAppInfoActivity.class);
        intent.putExtra(MYTYPE, type);
        context.startActivity(intent);
    }

    protected int setLayoutResourceID() {
        return R.layout.activity_my_app_info;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.myinfo_statlayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected boolean beforeWindow(Bundle savedInstanceState) {
        if (TextUtils.isEmpty(getIntent().getStringExtra(MYTYPE))) {
            return true;
        }
        return super.beforeWindow(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        mSWPullRecyclerLayout = optionView(R.id.myinfo_recyle);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mPullRefreshLayout = optionView(R.id.layout_pull);
        mRecyclerView = optionView(R.id.yappinfp_recyleview);
        mPullRefreshLayout.setLoadMoreEnable(true);
        mPullRefreshLayout.setFooterView(new FootView(this));
        mPullRefreshLayout.setHeaderView(new HeadView(this));


        String title = getString(R.string.zuixingonggao);
        switch (getIntent().getStringExtra(MYTYPE)) {
            case MYTYPE1:
                break;
            case MYTYPE2:
                title = getString(R.string.fuwuxieyi);
                break;
            case MYTYPE3:
                title = getString(R.string.pingtaijiangcheng);
                break;
            case MYTYPE4:
                title = getString(R.string.xuxiyuandi);
                break;
            case MYTYPE5:
                title = getString(R.string.pingfenshuoming);
                break;
            default:
                break;
        }
        mTextViewTitle.setText(title);
        startRefresh(true);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh(false);
            }

            @Override
            public void onLoading() {
                startRefresh(false);
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    protected MyAppInfoActivityPresenter mPresenterCreate() {
        DaggerMyAppInfoCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mMyAppInfoActivityPresenter;
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        Map<String, String> map = new ArrayMap<>();
        map.put(MYTYPE, getIntent().getStringExtra(MYTYPE));
        mMyAppInfoActivityPresenter.getData(map, isShowLoadingView);
    }

    @Override
    public void showData(MyappInfo myappInfo) {
        mPullRefreshLayout.refreshComplete();
        mPullRefreshLayout.loadMoreComplete();
        if (adapter == null) {
            adapter = new MyAppInfoAdapter(this, myappInfo.getData());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new LandingAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        } else {
            adapter.recyleList(myappInfo.getData());
//            adapter.addList(myappInfo.getData());
        }
    }
}
