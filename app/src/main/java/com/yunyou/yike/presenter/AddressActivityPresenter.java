package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.AddressCity;
import com.yunyou.yike.entity.City;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;

/**
 * Created by ${王俊强} on 2017/5/31.
 */

public class AddressActivityPresenter extends BasePresenter<IView.IAddressActivityView> implements
        IPresenter.IAddressActivityPrenester {

    public AddressActivityPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getAddressHot() {
        mApi.get_double_addr()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showAddressHotDataError(errorMsg);
                    }

                    @Override
                    public void onNext(String s) {
                        getView().showAddressHotData(mGson.fromJson(s, AddressCity.class));
                    }

                    @Override
                    protected void onSuccess(String s) {

                    }
                });
    }

    @Override
    public void getAddressCity() {
        mApi.region_info()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    public void onStart() {
                        getView().showLoodingView(null);
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showAddressCityDataError(errorMsg);
                    }


                    @Override
                    protected void onSuccess(String s) {
                        getView().showAddressCityData(mGson.fromJson(s, City.class));
                    }
                });
    }
}
