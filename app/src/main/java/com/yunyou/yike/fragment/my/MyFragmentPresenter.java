package com.yunyou.yike.fragment.my;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentPresenter implements IPrenester.IMyFragmentPrenester {
    private IView.IMyFragmentView mIMyFragmentView;
    private IModel.IMyFragmentModel mFragmentModel;

    public MyFragmentPresenter(IView.IMyFragmentView iMyFragmentView) {
        mIMyFragmentView = iMyFragmentView;
        mFragmentModel = new MyFragmentModel();
    }


    @Override
    public void getUserInfo() {
        mFragmentModel.getUserInfo(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {

            }

            @Override
            public void noNetWork(Object o) {

            }

            @Override
            public void success(Object o) {
                mIMyFragmentView.showUserInfo();
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
