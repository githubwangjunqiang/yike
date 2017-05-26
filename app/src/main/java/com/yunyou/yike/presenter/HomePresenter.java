package com.yunyou.yike.presenter;

import com.yunyou.yike.model.IModel;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.model.HomeFragmentModel;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class HomePresenter extends BasePresenter<IView.IHomeFragmentView> implements IPresenter.IHomeFragmentPrenester {
    private IModel.IHomeFragmentModel mFragmentModel;

    public HomePresenter() {
        mFragmentModel = new HomeFragmentModel();
    }


    @Override
    public void getBanner() {
        mFragmentModel.getBanner(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showLoodingView(null);
            }

            @Override
            public void noNetWork(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showNoNetworkView(null);
            }

            @Override
            public void success(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showBanner((BannerData) o);
            }

            @Override
            public void error(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showErrorView(o.toString());
            }

            @Override
            public void fail(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showErrorView(o);
            }
        });
    }

}
