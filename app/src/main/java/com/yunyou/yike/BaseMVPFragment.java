package com.yunyou.yike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.presenter.BasePresenter;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public abstract class BaseMVPFragment<V, A extends BasePresenter<V>> extends BaseMainFragment {
    private A mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = mPresenterCreate();
        if (!mPresenter.isViewAttached()) {
            mPresenter.attachView((V) this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract A mPresenterCreate();

    @Override
    public void onDetach() {
        mPresenter.detachView();
        super.onDetach();
    }

}
