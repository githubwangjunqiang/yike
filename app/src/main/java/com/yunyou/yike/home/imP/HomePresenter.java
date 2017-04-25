package com.yunyou.yike.home.imP;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.home.HomeFragmentModel;

import java.util.List;

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
            public void success(Object o) {
                mIView.showBanner((List<String>) o);
            }

            @Override
            public void error(Object o) {

            }

            @Override
            public void fail(Object o) {

            }
        });
    }

}
