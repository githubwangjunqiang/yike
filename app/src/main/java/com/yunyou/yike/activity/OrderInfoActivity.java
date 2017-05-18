package com.yunyou.yike.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.presenter.OrderInfoPresenter;

public class OrderInfoActivity extends BaseMVPActivity<IView.IOrderInfoActivityView, OrderInfoPresenter>
        implements IView.IOrderInfoActivityView {
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private SimpleDraweeView mDraweeView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDraweeView = optionView(R.id.order_info_iamgeview);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle.setText(R.string.dingdanxiangqing);
        mDraweeView.setImageURI(Uri.parse("http://p1.so.qhimgs1.com/bdr/326__/t01e5aeec8b812f1c31.jpg"));
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
    public void startRefresh(Object object) {
    }

    @Override
    public void showLoodingView(Object object) {

    }

    @Override
    public void showContentView(Object object) {

    }

    @Override
    public void showErrorView(Object object) {

    }

    @Override
    public void showEmptyView(Object object) {

    }

    @Override
    public void showNoNetworkView(Object object) {

    }

    @Override
    public void showTimeErrorView(Object object) {

    }

    @Override
    protected OrderInfoPresenter mPresenterCreate() {
        return new OrderInfoPresenter();
    }
}