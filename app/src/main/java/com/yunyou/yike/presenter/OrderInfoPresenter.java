package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class OrderInfoPresenter extends BasePresenter<IView.IOrderInfoActivityView> implements
        IPresenter.IOrderInfoPresenter {


    public OrderInfoPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }
}
