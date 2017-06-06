package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.Login;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class LoginActivityPresenter extends BasePresenter<IView.ILoginActivityView>
        implements IPresenter.ILoginPresenter {


    public LoginActivityPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void login(String mobile, String password, String device_num, String j_du, String w_du) {
        String[] sort = CallPostUtils.newBuilder()
                .addParamt("mobile", mobile)
                .addParamt("password", password)
                .addParamt("device_num", device_num)
                .addParamt("j_du", j_du)
                .addParamt("w_du", w_du)
                .build().sort();
        if (sort == null || sort.length < 2) {
            getView().ToToast("排序签名失败，请重试");
            return;
        }
        mApi.login(mobile, password, device_num, j_du, w_du, sort[0], sort[1])
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView())  {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().loginSuccess(mGson.fromJson(string, Login.class));
                    }
                });
    }
}
