package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import org.json.JSONObject;

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
        mApi.order_list(CallPostUtils.newBuilder().addMap(stringMap).getMap())
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
                        getView().showJobData(mGson.fromJson(string, JobList.class));
                    }
                });
    }

    @Override
    public void loodMoreJobData(Map<String, String> stringMap) {
        mApi.order_list(CallPostUtils.newBuilder().addMap(stringMap).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return false;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().ToToast(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().loodMoreJobData(mGson.fromJson(string, JobList.class));
                    }
                });
    }

    @Override
    public void confirmOrder(Map<String, String> stringMap) {
        mApi.confirm_order(CallPostUtils.newBuilder().addMap(stringMap).getMap())
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
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if (jsonObject.getInt("retcode") == 2000) {
                                getView().confirmOrderSucceess(jsonObject.getString("msg"));
                            } else {
                                getView().ToToast(jsonObject.getString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            getView().ToToast("解析失败，亲请重试.........");
                        }
                    }
                });
    }
}
