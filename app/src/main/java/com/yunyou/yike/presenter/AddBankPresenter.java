package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/6/20.
 */

public class AddBankPresenter extends BasePresenter<IView.IAddBankActivityView> implements
        IPresenter.IAddBankPresenter {
    public AddBankPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getBankName(Map<String, String> map) {
        mApi.get_bink_type(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return false;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            String data = jsonObject.getString("data");
                            if (data != null) {
                                getView().showBankName(data);
                            } else {
                                getView().ToToast("获取失败请重试");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("获取失败请重试");

                        }
                    }
                });
    }

    @Override
    public void bandBank(Map<String, String> map) {
        mApi.bind_bank(CallPostUtils.newBuilder().addMap(map).getMap())
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
                            JSONObject jsonObject = new JSONObject(string);
                            String msg = jsonObject.getString("msg");
                            getView().showBadBankSuccess(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("有点小小的异常需要处理，请您重试");
                        }
                    }
                });
    }
}
