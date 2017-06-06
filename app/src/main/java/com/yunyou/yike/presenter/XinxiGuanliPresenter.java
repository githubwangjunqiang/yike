package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class XinxiGuanliPresenter extends BasePresenter<IView.IxinxiGuanliActivityView> {
     public XinxiGuanliPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }
}
