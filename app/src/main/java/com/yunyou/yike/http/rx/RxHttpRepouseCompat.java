package com.yunyou.yike.http.rx;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.http.exception.RxApiExceptionRx;
import com.yunyou.yike.http.exception.RxBaseException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${王俊强} on 2017/5/24.
 */

public class RxHttpRepouseCompat {
    public static  Observable.Transformer<JsonObject, String> compatResult() {
        return new Observable.Transformer<JsonObject, String>() {
            @Override
            public Observable<String> call(Observable<JsonObject> httpBaseBeanObservable) {

                return httpBaseBeanObservable.flatMap(new Func1<JsonObject, Observable<String>>() {
                    @Override
                    public Observable<String> call(final JsonObject tRxHttpBaseBean) {
                        if (!TextUtils.isEmpty(tRxHttpBaseBean.toString())) {
                            try {
                                int retcode = tRxHttpBaseBean.get(RxHttpConstant.RETCODE).getAsInt();
                                String msg = tRxHttpBaseBean.get(RxHttpConstant.MSG).getAsString();
                                if (retcode == RxHttpConstant.TOKEN_ERROR) {//token失败
                                    return Observable.error(new RxApiExceptionRx(
                                            RxBaseException.API_ERROR,
                                            RxBaseException.API_ERRORTOKEN_CODE, msg));
                                } else if (retcode == RxHttpConstant.TOKEN_TIMEOUT) {//token过期
                                    return Observable.error(new RxApiExceptionRx(
                                            RxBaseException.API_ERROR,
                                            RxBaseException.API_TOKENOUT_CODE, msg));
                                } else if (retcode == RxHttpConstant.SUCCESS) {// 成功
                                    return Observable.create(new Observable.OnSubscribe<String>() {
                                        @Override
                                        public void call(Subscriber<? super String> subscriber) {
                                            if (!subscriber.isUnsubscribed()) {
                                                subscriber.onNext(tRxHttpBaseBean.toString());
                                            }
                                        }
                                    });

                                } else {
                                    return Observable.error(new RxApiExceptionRx(//请求失败
                                            RxBaseException.API_ERROR, msg));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return Observable.error(e);
                            }
                        } else {
                            return Observable.error(new RxApiExceptionRx(RxBaseException.API_ERROR,
                                    "服务器接口返回失败"));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }
//    public static <T> Observable.Transformer<RxHttpBaseBean<T>, T> compatResult() {
//        return new Observable.Transformer<RxHttpBaseBean<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<RxHttpBaseBean<T>> httpBaseBeanObservable) {
//                return httpBaseBeanObservable.flatMap(new Func1<RxHttpBaseBean<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(final RxHttpBaseBean<T> tRxHttpBaseBean) {
//                        if (tRxHttpBaseBean.isSuccess()) {
//                           return Observable.create(new Observable.OnSubscribe<T>() {
//                                @Override
//                                public void call(Subscriber<? super T> subscriber) {
//                                    try {
//                                        subscriber.onNext(tRxHttpBaseBean.getData());
//                                        subscriber.onCompleted();
//                                    } catch (Exception e) {
//                                        subscriber.onError(e);
//                                    }
//                                }
//                            });
//                        } else {
//                            return Observable.error(new RxApiExceptionRx(tRxHttpBaseBean.getRetcode(),
//                                    tRxHttpBaseBean.getMsg()));
//                        }
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
//            }
//        };
//    }
}
