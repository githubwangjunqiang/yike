package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.Jiedan;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class ALLOrderFragmentPresenter extends BasePresenter<IView.IAllOrderFragmentView>
        implements IPresenter.IAllOrderFragmentPrenester {

    public ALLOrderFragmentPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getOrder(Map map, final boolean isShowLoading) {
        mApi.send_order_manage(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

                    @Override
                    public void onStart() {
                        if (isShowLoading) {
                            getView().showLoodingView("加载中...");
                        }
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showOrder(mGson.fromJson(string, Order.class));
                    }
                });
    }

    @Override
    public void getMyJiedanOrder(Map map, final boolean isShowLoading) {
        mApi.accept_order_manage(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }
                    @Override
                    public void onStart() {
                        if (isShowLoading) {
                            getView().showLoodingView("加载中...");
                        }
                    }
                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showJiedanOrder(mGson.fromJson(string, Jiedan.class));
                    }
                });
    }

    @Override
    public void loodOrder(Map map) {
        mApi.send_order_manage(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return false;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast("没有更多了");
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().loodOrder(mGson.fromJson(string, Order.class));
                    }
                });
    }

    @Override
    public void loodMyjieOrder(Map map) {
        mApi.accept_order_manage(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return false;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast("没有更多了");
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().loodJiedanOrder(mGson.fromJson(string, Jiedan.class));
                    }
                });
    }

    @Override
    public void workStart(Map map, final int postion) {
        mApi.work_start(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return true;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().startSuccess(postion);
                    }
                });
    }

    @Override
    public void workCancel(Map map, final int position) {
        mApi.work_cancel(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return true;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().canleSuccess(position);
                    }
                });
    }
}