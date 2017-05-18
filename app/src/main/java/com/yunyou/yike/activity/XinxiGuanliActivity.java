package com.yunyou.yike.activity;

import android.os.Bundle;

import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.presenter.XinxiGuanliPresenter;

public class XinxiGuanliActivity extends BaseMVPActivity<IView.IxinxiGuanliActivityView, XinxiGuanliPresenter>
        implements IView.IxinxiGuanliActivityView {

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_xinxi_guanli;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

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
    protected XinxiGuanliPresenter mPresenterCreate() {
        return null;
    }
}
