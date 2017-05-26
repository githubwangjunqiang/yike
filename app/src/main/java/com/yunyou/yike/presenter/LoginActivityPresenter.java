package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.utils.LogUtils;

import javax.inject.Inject;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class LoginActivityPresenter extends BasePresenter<IView.ILoginActivityView>
        implements IPresenter.ILoginPresenter{
    @Inject
    RxApi mApi;

    public LoginActivityPresenter(RxApi api) {
        mApi = api;
    }


    @Override
    public void login(String mobile, String password, String device_num, String j_du, String w_du,
                      String time) {

        LogUtils.d("mApi="+mApi.hashCode());


//        mApi.login(mobile,password,device_num,j_du,w_du,time,sign);
    }
}
