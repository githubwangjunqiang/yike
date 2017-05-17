package com.yunyou.yike.fragment.home.imP;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.fragment.home.HomeFragmentModel;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class HomePresenter implements IPrenester.IHomeFragmentPrenester {
    private IView.IHomeFragmentView mIView;
    private IModel.IHomeFragmentModel mFragmentModel;

    public HomePresenter(IView.IHomeFragmentView iView) {
        mIView = iView;
        mFragmentModel = new HomeFragmentModel();
    }


    @Override
    public void getBanner() {
        mFragmentModel.getBanner(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
                mIView.showLoodingView(null);
            }

            @Override
            public void noNetWork(Object o) {
                mIView.showNoNetworkView(null);
            }

            @Override
            public void success(Object o) {
                mIView.showBanner((BannerData) o);
            }

            @Override
            public void error(Object o) {
                mIView.showErrorView(o.toString());
            }

            @Override
            public void fail(Object o) {
                mIView.showErrorView(o);
            }
        });
    }

}
