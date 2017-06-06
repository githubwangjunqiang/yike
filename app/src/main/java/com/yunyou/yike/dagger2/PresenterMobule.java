package com.yunyou.yike.dagger2;


import com.google.gson.Gson;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.presenter.AddressActivityPresenter;
import com.yunyou.yike.presenter.DecorationWorkerPresenter;
import com.yunyou.yike.presenter.HomePresenter;
import com.yunyou.yike.presenter.LoginActivityPresenter;
import com.yunyou.yike.presenter.RegisterActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${王俊强} on 2017/5/23.
 */

@Module
public class PresenterMobule {

    @Provides
    public RegisterActivityPresenter gprovideRegisterActivityPresenter(RxApi mapi, Gson gson) {
        return new RegisterActivityPresenter(mapi,gson);
    }

    @Provides
    public DecorationWorkerPresenter provideDecorationWorkerPresenter(RxApi mapi, Gson gson) {
        return new DecorationWorkerPresenter(mapi, gson);
    }

    @Provides
    public AddressActivityPresenter gprovideAddressActivityPresenter(RxApi mapi, Gson gson) {
        return new AddressActivityPresenter(mapi,gson);
    }

    @Provides
    public LoginActivityPresenter gprovideLoginActivityPresenter(RxApi mapi, Gson gson) {
        return new LoginActivityPresenter(mapi,gson);
    }

    @Provides
    public HomePresenter gprovideHomePresenter(RxApi mapi, Gson gson) {
        return new HomePresenter(mapi,gson);
    }
}


