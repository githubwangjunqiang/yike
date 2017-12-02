package com.yunyou.yike.presenter;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.BankList;
import com.yunyou.yike.entity.Money;
import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.http.rx.RxExceptionSubscriber;
import com.yunyou.yike.http.rx.RxHttpRepouseCompat;
import com.yunyou.yike.utils.CallPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class QianBaoPresenter extends BasePresenter<IView.IQianBaoActivityView>
        implements IPresenter.IQianBaoPresenter {
    public QianBaoPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getMoney(Map<String, String> map, final boolean isShowLoading) {
        mApi.my_money(CallPostUtils.newBuilder().addMap(map).getMap())
                .compose(RxHttpRepouseCompat.compatResult())
                .subscribe(new RxExceptionSubscriber<String>(getView()) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return isShowLoading;
                    }

                    @Override
                    protected void apiError(int code, String errorMsg) {
                        getView().showErrorView(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String string) {
                        getView().showDataMoney(mGson.fromJson(string, Money.class));
                    }
                });
    }

    @Override
    public void setWithdrawCash(Map<String, String> mapBankList, final Map<String, String> mapTiXian) {

        mApi.bank_list(CallPostUtils.newBuilder().addMap(mapBankList).getMap())
                .flatMap(new Func1<JsonObject, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(JsonObject jsonObject) {
                        try {
                            if (new JSONObject(jsonObject.toString())
                                    .getInt(RxHttpConstant.RETCODE) == RxHttpConstant.SUCCESS) {
                                List<BankList.DataBean> data = mGson.fromJson(jsonObject, BankList.class).getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).getIs_default().equals("1")) {
                                        mapTiXian.put("bank_id", data.get(i).getId());
                                        return mApi.withdraw_cash(CallPostUtils.newBuilder().addMap(mapTiXian).getMap());
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            return Observable.error(e);
                        }
                        return Observable.error(new Resources.NotFoundException("没有绑定银行卡"));
                    }
                }).compose(RxHttpRepouseCompat.compatResult())
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
                            getView().showWithdraw(new JSONObject(string).getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getView().ToToast("解析失败，请重试");
                        }
                    }
                });

    }

}
