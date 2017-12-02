package com.yunyou.yike.dagger2;


import com.google.gson.Gson;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.presenter.ALLOrderFragmentPresenter;
import com.yunyou.yike.presenter.AddBankPresenter;
import com.yunyou.yike.presenter.AddressActivityPresenter;
import com.yunyou.yike.presenter.BankPresenter;
import com.yunyou.yike.presenter.DecorationWorkerPresenter;
import com.yunyou.yike.presenter.HomePresenter;
import com.yunyou.yike.presenter.JobActivityPresenter;
import com.yunyou.yike.presenter.LoginActivityPresenter;
import com.yunyou.yike.presenter.MyAppInfoActivityPresenter;
import com.yunyou.yike.presenter.MyFragmentPresenter;
import com.yunyou.yike.presenter.OrderInfoPresenter;
import com.yunyou.yike.presenter.QianBaoPresenter;
import com.yunyou.yike.presenter.RegisterActivityPresenter;
import com.yunyou.yike.presenter.SendFeelPresenter;
import com.yunyou.yike.presenter.XinxiGuanliPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${王俊强} on 2017/5/23.
 */

@Module
public class PresenterMobule {

    @Provides
    public SendFeelPresenter provideSendFeelPresenter(RxApi mapi, Gson gson) {
        return new SendFeelPresenter(mapi, gson);
    }

    @Provides
    public OrderInfoPresenter gprovideOrderInfoPresenter(RxApi mapi, Gson gson) {
        return new OrderInfoPresenter(mapi, gson);
    }

    @Provides
    public ALLOrderFragmentPresenter gprovideALLOrderFragmentPresenter(RxApi mapi, Gson gson) {
        return new ALLOrderFragmentPresenter(mapi, gson);
    }

    @Provides
    public AddBankPresenter gprovideAddBankPresenter(RxApi mapi, Gson gson) {
        return new AddBankPresenter(mapi, gson);
    }

    @Provides
    public BankPresenter gprovideBankPresenter(RxApi mapi, Gson gson) {
        return new BankPresenter(mapi, gson);
    }

    @Provides
    public MyAppInfoActivityPresenter gprovideMyAppInfoActivityPresenter(RxApi mapi, Gson gson) {
        return new MyAppInfoActivityPresenter(mapi, gson);
    }

    @Provides
    public XinxiGuanliPresenter gprovideXinxiGuanliPresenter(RxApi mapi, Gson gson) {
        return new XinxiGuanliPresenter(mapi, gson);
    }

    @Provides
    public QianBaoPresenter gprovideQianBaoPresenter(RxApi mapi, Gson gson) {
        return new QianBaoPresenter(mapi, gson);
    }

    @Provides
    public MyFragmentPresenter gprovideMyFragmentPresenter(RxApi mapi, Gson gson) {
        return new MyFragmentPresenter(mapi, gson);
    }

    @Provides
    public JobActivityPresenter gprovideJobActivityPresenter(RxApi mapi, Gson gson) {
        return new JobActivityPresenter(mapi, gson);
    }

    @Provides
    public RegisterActivityPresenter gprovideRegisterActivityPresenter(RxApi mapi, Gson gson) {
        return new RegisterActivityPresenter(mapi, gson);
    }

    @Provides
    public DecorationWorkerPresenter provideDecorationWorkerPresenter(RxApi mapi, Gson gson) {
        return new DecorationWorkerPresenter(mapi, gson);
    }

    @Provides
    public AddressActivityPresenter gprovideAddressActivityPresenter(RxApi mapi, Gson gson) {
        return new AddressActivityPresenter(mapi, gson);
    }

    @Provides
    public LoginActivityPresenter gprovideLoginActivityPresenter(RxApi mapi, Gson gson) {
        return new LoginActivityPresenter(mapi, gson);
    }

    @Provides
    public HomePresenter gprovideHomePresenter(RxApi mapi, Gson gson) {
        return new HomePresenter(mapi, gson);
    }
}


