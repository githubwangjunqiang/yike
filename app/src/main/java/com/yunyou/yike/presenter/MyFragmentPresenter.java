package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.model.IModel;
import com.yunyou.yike.model.MyFragmentModel;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class MyFragmentPresenter extends BasePresenter<IView.IMyFragmentView> implements IPresenter.IMyFragmentPrenester {
    private IModel.IMyFragmentModel mFragmentModel;

    public MyFragmentPresenter() {
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
                if (getView() == null) {
                    return;
                }
                getView().showUserInfo();
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
