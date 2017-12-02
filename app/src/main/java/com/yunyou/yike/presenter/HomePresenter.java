package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.CityId;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class HomePresenter extends BasePresenter<IView.IHomeFragmentView>
        implements IPresenter.IHomeFragmentPrenester {

    public HomePresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getBanner(final boolean isShowLoading) {
        mApi.index_banner()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showBanner(mGson.fromJson(string, BannerData.class));
                    }
                });
    }

    @Override
    public void getCityID(Map<String, String> map, final boolean isShowLoading) {
        mApi.gps_addr(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast("获取当前城市id失败，亲请刷新界面试试呢....");
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showCityIDSuccess(mGson.fromJson(string, CityId.class));
                    }
                });
    }

}
