package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class ALLOrderFragmentPresenter extends BasePresenter<IView.IAllOrderFragmentView>
        implements IPresenter.IAllOrderFragmentPrenester {

    public ALLOrderFragmentPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getOrder() {
    }

    @Override
    public void loodOrder() {
    }
}
