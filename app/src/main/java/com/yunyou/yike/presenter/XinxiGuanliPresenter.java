package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.Bean;
import com.yunyou.yike.entity.HappyList;
import com.yunyou.yike.entity.MyUserInfo;
import com.yunyou.yike.entity.UnData;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import java.util.Map;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class XinxiGuanliPresenter extends BasePresenter<IView.IxinxiGuanliActivityView>
        implements IPresenter.IXinxiGuanliPresenter {
    public XinxiGuanliPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getUserInfo(final boolean isShowload, Map<String, String> map) {
        mApi.post(Bean.get_user_info, CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowload;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showUserinfo(mGson.fromJson(string, MyUserInfo.class));
                    }
                });
    }

    @Override
    public void getHobbyList(Map<String, String> map) {
        mApi.post(Bean.get_hobby, CallPostUtils.newBuilder().addMap(map).getMap())
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
                        getView().showHappyList(mGson.fromJson(string, HappyList.class));
                    }
                });

    }

    @Override
    public void updata_info(Map<String, String> map) {
        mApi.post(Bean.updata_info, CallPostUtils.newBuilder().addMap(map).getMap())
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
                        getView().showUnDataSuccess(mGson.fromJson(string, UnData.class).getMsg());

                    }
                });
    }

    @Override
    public void getWorkerType() {
        mApi.work_type().compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return true;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showWorkerType(mGson.fromJson(string, WorkerType.class));
                    }
                });
    }
}
