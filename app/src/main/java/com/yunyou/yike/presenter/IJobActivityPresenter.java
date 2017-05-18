package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.model.IModel;
import com.yunyou.yike.model.JobActivityModel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class IJobActivityPresenter extends BasePresenter<IView.IJobActivityView>
        implements IPresenter.IJobPresenter {
    private IModel.IJobActivityModel mModel;

    public IJobActivityPresenter() {
        mModel = new JobActivityModel();
    }

    @Override
    public void getJobData(final boolean isShowLoading) {
        mModel.getJobData(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {
                if(isShowLoading){
                    getView().showLoodingView(null);
                }
            }

            @Override
            public void noNetWork(Object o) {
                getView().showNoNetworkView(o);
            }

            @Override
            public void success(Object o) {
                getView().showContentView(o);
                getView().showJobData((List<JobList>) o);
            }

            @Override
            public void error(Object o) {
                getView().showErrorView(o);
            }

            @Override
            public void fail(Object o) {
                getView().showEmptyView(o);
            }
        });
    }

    @Override
    public void loodMoreJobData() {

    }
}