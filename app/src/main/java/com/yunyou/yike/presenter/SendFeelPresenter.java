package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/6/28.
 */

public class SendFeelPresenter extends BasePresenter<IView.ISendFeelActivityView> implements
        IPresenter.ISendFeelPrenester {
    public SendFeelPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void subMitFeelData() {

    }
}
