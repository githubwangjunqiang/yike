package com.yunyou.yike.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class HomePresenter extends BasePresenter<IView.IHomeFragmentView> implements IPresenter.IHomeFragmentPrenester {
    private RxApi mApi;

    public HomePresenter(@NonNull RxApi api) {
        mApi = api;
    }

    @Override
    public void getBanner() {
        mApi.index_banner()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showBanner(new Gson().fromJson(string, BannerData.class));
                    }
                });
    }

}
