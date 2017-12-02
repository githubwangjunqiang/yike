package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BankList;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import rx.Subscription;

/**
 * Created by ${王俊强} on 2017/6/20.
 */

public class BankPresenter extends BasePresenter<IView.IBankActivityView> implements
        IPresenter.IBankPresenter {
    public BankPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getBankList(Map<String, String> map, final boolean isShowLoading) {
        Subscription subscribe = mApi.bank_list(CallPostUtils.newBuilder().addMap(map).getMap())
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
                        getView().showBankList(mGson.fromJson(string, BankList.class));
                    }
                });
    }

    @Override
    public void setDefaultBank(Map<String, String> map) {
        mApi.set_default_bank(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return true;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        try {
                            JSONObject object = new JSONObject(string);
                            getView().showdefaultBank(object.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("亲请重试 ，解析失败");
                        }
                    }
                });
    }
}
