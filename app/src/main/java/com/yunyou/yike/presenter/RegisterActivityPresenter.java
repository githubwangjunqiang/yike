package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BaseSort;
import com.yunyou.yike.entity.SendCode;
import com.yunyou.yike.entity.User;
import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.MD5Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
        LogUtils.d(""+mApi.hashCode());
        mApi.send(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
//                .compose(RxHttpRepouseCompat.<SendCode>compatResult())
                .subscribe(new RxExceptionSubscriber<SendCode>((WeakReference<IView>) getView()) {
                    @Override
                    protected void apiError(String errorMsg) {
                        getView().showContentView(null);
                        getView().sendCodeError(errorMsg);
                    }

                    @Override
                    public void onNext(SendCode sendCode) {
                        if (sendCode.isSuccess()) {
                            getView().showContentView(null);
                            getView().sendCodeSuccess(sendCode.getData());
                        } else {
                            apiError(sendCode.getMsg());
                        }
                    }
                });

    }

    @Override
    public void registerUser(String mobile, String password, String password2, String code, String time) {

        List<BaseSort> list = new ArrayList<>();
        list.add(new BaseSort("mobile",mobile));
        list.add(new BaseSort("password",password));
        list.add(new BaseSort("password2",password2));
        list.add(new BaseSort("code",code));
        Collections.sort(list, new Comparator<BaseSort>() {
            @Override
            public int compare(BaseSort o1, BaseSort o2) {

                return o1.getKey().compareTo(o2.getKey());
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).getContent());
        }
        stringBuilder.append(time).append(RxHttpConstant.KEY);
        LogUtils.d(stringBuilder.toString());
        String sign = MD5Utils.md5Code(stringBuilder.toString());
        LogUtils.d("加密="+sign);
        mApi.register(mobile, password, password2, code, time, sign)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new RxExceptionSubscriber<User>((WeakReference<IView>) getView()) {
                    @Override
                    protected void apiError(String errorMsg) {
                        if (getView() != null) {
                            getView().showContentView(null);
                            getView().registerError(errorMsg);
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        if (getView() != null) {
                            getView().showContentView(null);
                            if (user.isSuccess()) {
                                getView().registerSuccess(user);
                            } else {
                                getView().registerError(user.getMsg());
                            }
                        }
                    }
                });
    }
}