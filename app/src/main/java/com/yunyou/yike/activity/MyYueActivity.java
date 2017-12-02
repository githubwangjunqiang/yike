package com.yunyou.yike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;

public class MyYueActivity extends BaseActivity {
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;

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
        return R.layout.activity_my_yue;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle.setText(R.string.wodeqianbao);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    /**
     * 我的余额
     *
     * @param view
     */
    public void onClickYue(View view) {
        startActivity(new Intent(this, QianbaoActivity.class));
    }

    /**
     * 我的银行卡
     *
     * @param view
     */
    public void onClickBank(View view) {
        startActivity(new Intent(this, BankActivity.class));
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }
}
