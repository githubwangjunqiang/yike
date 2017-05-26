package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.model.FeelFragmentModel;
import com.yunyou.yike.model.IModel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class FeelFragmentPresenter extends BasePresenter<IView.IFeelFragmentView>
        implements IPresenter.IFeelFragmentPrenester {
    private IModel.IFeelFragmentModel mIFeelFragmentModel;

    public FeelFragmentPresenter() {
        mIFeelFragmentModel = new FeelFragmentModel();
    }

    @Override
    public void getFeelData(final boolean isShowLoading) {
        mIFeelFragmentModel.getFeelData(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
                if (isShowLoading) {
                    if (getView() == null) {
                        return;
                    }
                    getView().showLoodingView(null);
                }
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
                getView().showFeelData((List<Feel>) o);
                getView().showContentView(null);
            }

            @Override
            public void error(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showLoodingView(o);
            }

            @Override
            public void fail(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().showTimeErrorView(o);
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
                if (getView() == null) {
                    return;
                }
                getView().loodMoreFeelData(null);
            }

            @Override
            public void success(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().loodMoreFeelData((List<Feel>) o);
            }

            @Override
            public void error(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().loodMoreFeelData(null);
            }

            @Override
            public void fail(Object o) {
                if (getView() == null) {
                    return;
                }
                getView().loodMoreFeelData(null);
            }
        });
    }
}
