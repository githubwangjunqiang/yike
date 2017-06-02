package com.yunyou.yike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerWorkerCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.DecorationWorkerPresenter;

import javax.inject.Inject;

public class DecorationWorkerActivity extends BaseMVPActivity<IView.IDecorationWorkerView,
        DecorationWorkerPresenter> implements IView.IDecorationWorkerView {
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    @Inject
    DecorationWorkerPresenter mPresenter;

    @Override
    protected int getStateLayoutID() {
        return R.id.worker_statelayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.lock_worker_layout;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_decoration_worker;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle.setText(R.string.zhuangxiugongren);
        mTextViewTitle.setText(R.string.wanshandingdan);
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

    @Override
    public void startRefresh(Object object) {
        showContentView(null);
    }

    @Override
    protected DecorationWorkerPresenter mPresenterCreate() {
        DaggerWorkerCompcoent.builder().presenterMobule(new PresenterMobule())
                .appCompcoent(((App) getApplication()).getAppCompcoent())
                .build().inJect(this);
        return mPresenter;
    }
}
