package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class JobActivityPresenter extends BasePresenter<IView.IJobActivityView>
        implements IPresenter.IJobPresenter {


    public JobActivityPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getJobData(final boolean isShowLoading) {
    }

    @Override
    public void loodMoreJobData() {

    }
}
