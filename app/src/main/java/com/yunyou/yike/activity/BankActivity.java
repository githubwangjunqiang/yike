package com.yunyou.yike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.BankAdapter;
import com.yunyou.yike.dagger2.DaggerBankCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.BankList;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.BankPresenter;
import com.yunyou.yike.ui_view.pulllayout.HeadView;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.util.Map;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class BankActivity extends BaseMVPActivity<IView.IBankActivityView, BankPresenter>
        implements IView.IBankActivityView {
    @Inject
    BankPresenter mBankPresenter;
    private FrameLayout mFrameLayoutAddBank;
    private BankAdapter mBankAdapter;
    private RecyclerView mRecyclerView;
    private PullRefreshLayout mPullRefreshLayout;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private static final int BANKCOED = 0x1001;
    private Button mButtonEditing;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_bank;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.bank_statlayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFrameLayoutAddBank = optionView(R.id.add_bank_fragmentlayout);
        mRecyclerView = optionView(R.id.bank_recyclerview);
        mPullRefreshLayout = optionView(R.id.bank_pulllayout);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mButtonEditing = optionView(R.id.title_btn);
        mTextViewTitle.setText(R.string.mybank);
        mButtonEditing.setVisibility(View.VISIBLE);
        mButtonEditing.setText("编辑默认卡");
        mPullRefreshLayout.setHeaderView(new HeadView(this));
//        mPullRefreshLayout.setFooterView(new FootView(this));
        mPullRefreshLayout.setLoadMoreEnable(false);
        startRefresh(true);
    }

    @Override
    protected void setListener() {
        mFrameLayoutAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加新的银行卡
                Intent intent = new Intent(BankActivity.this, AddBankActivity.class);
                startActivityForResult(intent, BANKCOED);
            }
        });
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//刷新操作
                startRefresh(false);
            }
        });
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//返回按钮
                finish();
            }
        });
        mButtonEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//编辑默认卡
                if (mBankAdapter != null && mBankAdapter.getItemCount() > 0) {

                    if (mBankAdapter.isEditing()) {
                        mBankAdapter.setEditing(false);
                        mButtonEditing.setText("编辑默认卡");
                        mButtonEditing.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mBankAdapter.isDelft()) {
                                    String id = mBankAdapter.getId();
                                    if (TextUtils.isEmpty(id)) {
                                        To.oo("您还没选择默认卡号");
                                        return;
                                    }
                                    Map<String, String> map = new ArrayMap<String, String>();
                                    map.put("id", id);
                                    map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                                    mBankPresenter.setDefaultBank(map);
                                }
                            }
                        }, 500);
                    } else {
                        mBankAdapter.setEditing(true);
                        mButtonEditing.setText("完成");
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BANKCOED && resultCode == RESULT_OK) {
            mPullRefreshLayout.autoRefresh();
        }
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    protected BankPresenter mPresenterCreate() {
        DaggerBankCompcoent.builder()
                .appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mBankPresenter;
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mBankPresenter.getBankList(map, isShowLoadingView);
    }

    @Override
    public void showBankList(BankList bankList) {
        mPullRefreshLayout.refreshComplete();
        if (mBankAdapter != null) {
            mBankAdapter.recyleList(bankList.getData());
        } else {
            mBankAdapter = new BankAdapter(this, bankList.getData());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new LandingAnimator());
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(mBankAdapter);
        }
    }

    @Override
    public void showdefaultBank(String strings) {
        To.ss(mRecyclerView, strings);
    }
}
