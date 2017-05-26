package com.yunyou.yike.http.rx;

import com.yunyou.yike.http.entity.RxHttpBaseBean;
import com.yunyou.yike.http.exception.RxApiExceptionRx;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${王俊强} on 2017/5/24.
 */

public class RxHttpRepouseCompat {
    public static <T> Observable.Transformer<RxHttpBaseBean<T>, T> compatResult() {
        return new Observable.Transformer<RxHttpBaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<RxHttpBaseBean<T>> httpBaseBeanObservable) {
                return httpBaseBeanObservable.flatMap(new Func1<RxHttpBaseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final RxHttpBaseBean<T> tRxHttpBaseBean) {
                        if (tRxHttpBaseBean.isSuccess()) {
                           return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    try {
                                        subscriber.onNext(tRxHttpBaseBean.getData());
                                        subscriber.onCompleted();
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        } else {
                            return Observable.error(new RxApiExceptionRx(tRxHttpBaseBean.getRetcode(),
                                    tRxHttpBaseBean.getMsg()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }
}
