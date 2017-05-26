package com.yunyou.yike;

import com.yunyou.yike.presenter.BasePresenter;

/**
 * Created by ${王俊强} on 2017/4/19.AutoLayoutActivity
 */

public abstract class BaseMVPActivity<V, A extends BasePresenter<V>> extends BaseActivity {

    protected A mPresenter;

    @Override
    protected void setPresenter() {
        super.setPresenter();
        mPresenter = mPresenterCreate();



        if (mPresenter == null) {
            throw new NullPointerException("请把管理者实现类new出来呦");
        }
        if (!mPresenter.isViewAttached()) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract A mPresenterCreate();

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
