package com.yunyou.yike.dagger2;


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
    public RegisterActivityPresenter gprovidesRegisterActivityPresenter(RxApi mapi) {
        return new RegisterActivityPresenter(mapi);
    }

    @Provides
    public DecorationWorkerPresenter providesDecorationWorkerPresenter(RxApi mapi) {
        return new DecorationWorkerPresenter(mapi);
    }
    @Provides
    public AddressActivityPresenter gprovidesAddressActivityPresenter(RxApi mapi) {
        return new AddressActivityPresenter(mapi);
    }
    @Provides
    public LoginActivityPresenter gprovidesLoginActivityPresenter(RxApi mapi) {
        return new LoginActivityPresenter(mapi);
    }
    @Provides
    public HomePresenter gprovidesHomePresenter(RxApi mapi) {
        return new HomePresenter(mapi);
    }
}


