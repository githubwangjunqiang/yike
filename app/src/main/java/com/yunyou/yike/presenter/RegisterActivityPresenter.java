package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.User;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;
import com.yunyou.yike.utils.To;


/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class RegisterActivityPresenter extends BasePresenter<IView.IRegisterActivityView> implements
        IPresenter.IRegisterPresenter {
    private RxApi mApi;

    public RegisterActivityPresenter(RxApi api) {
        mApi = api;
    }

    @Override
    public void sendCode(String phone) {
        mApi.send(phone)
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showContentView(null);
                        getView().sendCodeError(errorMsg);
                    }


                    @Override
                    protected void onSuccess(String s) {
                        getView().sendCodeSuccess(s.toString());
                    }
                });

    }

    @Override
    public void registerUser(String mobile, String password, String password2, String code) {

        String[] sort = CallPostUtils.newBuilder()
                .addParamt("mobile", mobile)
                .addParamt("password", password)
                .addParamt("password2", password2)
                .addParamt("code", code)
                .build().sort();
        if (sort == null) {
            To.ee("排序签名失败请重试");
            return;
        }

        mApi.register(mobile, password, password2, code, sort[0], sort[1])
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        if (getView() != null) {
                            getView().showContentView(null);
                            getView().registerError(errorMsg);
                        }
                    }


                    @Override
                    protected void onSuccess(String user) {
                        if (getView() != null) {
                            getView().registerSuccess(new Gson().fromJson(user, User.class));
                        }
                    }
                });
    }
}