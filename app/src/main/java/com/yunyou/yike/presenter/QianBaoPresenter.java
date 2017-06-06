package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class QianBaoPresenter extends BasePresenter<IView.IQianBaoActivityView> {
    public QianBaoPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }
}
