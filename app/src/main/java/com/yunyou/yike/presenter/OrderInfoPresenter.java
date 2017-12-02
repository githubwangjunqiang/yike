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
 * Created by ${王俊强} on 2017/5/18.
 */

public class OrderInfoPresenter extends BasePresenter<IView.IOrderInfoActivityView> implements
        IPresenter.IOrderInfoPresenter {


    public OrderInfoPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void canleOrder(Map map) {
        mApi.work_cancel(CallPostUtils.newBuilder().addMap(map).getMap())
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
                            getView().ToToast(new JSONObject(string).getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("解析异常");
                        }
                    }
                });
    }

    @Override
    public void finishOrder(Map map) {
        mApi.finish_order(CallPostUtils.newBuilder().addMap(map).getMap())
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
                            getView().ToToast(new JSONObject(string).getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("请重试，解析失败");

                        }
                    }
                });
    }
}
