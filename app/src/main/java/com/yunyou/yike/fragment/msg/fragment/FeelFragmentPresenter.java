package com.yunyou.yike.fragment.msg.fragment;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.fragment.msg.model.FeelFragmentModel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class FeelFragmentPresenter implements IPrenester.IFeelFragmentPrenester {
    private IView.IFeelFragmentView mIFeelFragmentView;
    private IModel.IFeelFragmentModel mIFeelFragmentModel;

    public FeelFragmentPresenter(IView.IFeelFragmentView IFeelFragmentView) {
        mIFeelFragmentView = IFeelFragmentView;
        mIFeelFragmentModel = new FeelFragmentModel();
    }

    @Override
    public void getFeelData(final boolean isShowLoading) {
        mIFeelFragmentModel.getFeelData(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
                if(isShowLoading){
                    mIFeelFragmentView.showLoodingView(null);
                }
            }

            @Override
            public void noNetWork(Object o) {
                mIFeelFragmentView.showNoNetworkView(null);
            }

            @Override
            public void success(Object o) {
                mIFeelFragmentView.showFeelData((List<Feel>) o);
                mIFeelFragmentView.showContentView(null);
            }

            @Override
            public void error(Object o) {
                mIFeelFragmentView.showLoodingView(o);
            }

            @Override
            public void fail(Object o) {
                mIFeelFragmentView.showTimeErrorView(o);
            }
        });
    }

    @Override
    public void loodMoreFeelData() {
        mIFeelFragmentModel.getFeelData(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
            }

            @Override
            public void noNetWork(Object o) {
                mIFeelFragmentView.loodMoreFeelData(null);
            }

            @Override
            public void success(Object o) {
                mIFeelFragmentView.loodMoreFeelData((List<Feel>) o);
            }

            @Override
            public void error(Object o) {
                mIFeelFragmentView.loodMoreFeelData(null);
            }

            @Override
            public void fail(Object o) {
                mIFeelFragmentView.loodMoreFeelData(null);
            }
        });
    }
}
