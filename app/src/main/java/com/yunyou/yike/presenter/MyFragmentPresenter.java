package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentPresenter extends BasePresenter<IView.IMyFragmentView> implements IPresenter.IMyFragmentPrenester {

    public MyFragmentPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }


    @Override
    public void getUserInfo() {
    }
}
