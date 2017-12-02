package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.KeFu;
import com.yunyou.yike.entity.MyUserInfo;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentPresenter extends BasePresenter<IView.IMyFragmentView> implements
        IPresenter.IMyFragmentPrenester {

    public MyFragmentPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }


    @Override
    public void getUserInfo(Map<String, String> map, final boolean isShowLoading) {
        mApi.get_user_info(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showUserInfo(mGson.fromJson(string, MyUserInfo.class));
                    }
                });
    }

    @Override
    public void getCustomerService() {
        mApi.contact_us()
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
                        getView().showCustomerService(mGson.fromJson(string, KeFu.class));
                    }
                });
    }

    @Override
    public void setfeedback(Map<String, String> map) {
        mApi.feedback(CallPostUtils.newBuilder().addMap(map).getMap())
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
                            getView().showSuggertions(new JSONObject(string).getString("msg"));
                        } catch (JSONException e) {
                            getView().ToToast("解析失败，亲不好意思请重试");
                            e.printStackTrace();
                        }
                    }
                });
    }
}
