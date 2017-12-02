package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.MyappInfo;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/6/16.
 */

public class MyAppInfoActivityPresenter extends BasePresenter<IView.IMyAppInfoActivityView>
        implements IPresenter.IMyAppInfoActivityPresenter {
    public MyAppInfoActivityPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getData(Map<String, String> map, final boolean isShowLoading) {
        mApi.get_news(map)
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

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
                        getView().showData(mGson.fromJson(string, MyappInfo.class));
                    }
                });
    }
}
