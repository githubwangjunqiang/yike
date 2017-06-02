package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/6/2.
 */

public class DecorationWorkerPresenter extends BasePresenter<IView.IDecorationWorkerView> implements
        IPresenter.IDecorationWorkerPresenter {
    private RxApi mApi;

    public DecorationWorkerPresenter(RxApi api) {
        mApi = api;
    }



}
