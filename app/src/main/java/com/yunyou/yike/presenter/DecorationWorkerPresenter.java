package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.WorkerStyle;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;

/**
 * Created by ${王俊强} on 2017/6/2.
 */

public class DecorationWorkerPresenter extends BasePresenter<IView.IDecorationWorkerView> implements
        IPresenter.IDecorationWorkerPresenter {


    public DecorationWorkerPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void loodWorkType() {
        mApi.work_type()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showWorkerTypeSuccess(mGson.fromJson(string, WorkerType.class));
                    }

                });
    }

    @Override
    public void loodWorkStyle() {
        mApi.style()
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showWorkerStyleSuccess(mGson.fromJson(string, WorkerStyle.class));
                    }
                });
    }
}
