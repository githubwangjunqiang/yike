package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class JobActivityPresenter extends BasePresenter<IView.IJobActivityView>
        implements IPresenter.IJobPresenter {


    public JobActivityPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getJobData(final boolean isShowLoading, Map<String, String> stringMap) {
        mApi.order_list(CallPostUtils.newBuilder().addMap(stringMap).build().getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    public void onStart() {
                        if (isShowLoading) {
                            getView().showLoodingView(null);
                        }
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
//                        getView().showJobData();
                    }
                });
    }

    @Override
    public void loodMoreJobData(Map<String, String> stringMap) {
        mApi.order_list(CallPostUtils.newBuilder().addMap(stringMap).build().getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String string) {

                    }
                });
    }
}
