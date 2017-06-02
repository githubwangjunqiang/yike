package com.yunyou.yike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.QianBaoPresenter;

public class QianbaoActivity extends BaseMVPActivity<IView.IQianBaoActivityView,
        QianBaoPresenter> implements IView.IQianBaoActivityView {
    private TextView mTextViewTitle;//标题
    private ImageView mImageViewBack;//返回按钮

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_qianbao;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.qianbao_staslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
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
     * 提现
     *
     * @param view
     */
    public void onClickWithdrawals(View view) {

    }

    /**
     * 充值
     *
     * @param view
     */
    public void onClickRecharge(View view) {
    }

    @Override
    public void startRefresh(Object object) {

    }


    @Override
    protected QianBaoPresenter mPresenterCreate() {
        return new QianBaoPresenter();
    }
}
