package com.yunyou.yike.http.rx;

import com.google.gson.JsonParseException;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.exception.RxApiExceptionRx;
import com.yunyou.yike.http.exception.RxBaseException;

import org.json.JSONException;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * Created by ${王俊强} on 2017/5/24.
 */

public abstract class RxExceptionSubscriber<E> extends RxBaseSubscriber<E> {
    private Reference<IView> mIView;

    public RxExceptionSubscriber(IView iView) {
        mIView = new WeakReference<>(iView);
    }


    @Override
    public void onStart() {
        super.onStart();
        mIView.get().showLoodingDialog(null);
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
//        RxBaseException baseException = new RxBaseException();

        if (e instanceof RxApiExceptionRx) {//是我们自己定义的异常
            mIView.get().hideDiaLogView();
            RxApiExceptionRx mRxApiExceptionRx = (RxApiExceptionRx) e;
            if (mRxApiExceptionRx.getHttpcode() == RxBaseException.API_TOKENOUT_CODE//5000登陆超时
                    || mRxApiExceptionRx.getHttpcode() == RxBaseException.API_ERRORTOKEN_CODE) {//3000token验证失败
                mIView.get().showLoginView(null);
            } else {
                apiError(mRxApiExceptionRx.getHttpcode(), mRxApiExceptionRx.getErrorMsg());
            }
        } else if (e instanceof SocketException) {//网络异常
            mIView.get().showNoNetworkView("网络异常");
        } else if (e instanceof SocketTimeoutException) {//网络超时
            mIView.get().showTimeErrorView(null);
        } else if (e instanceof HttpException) {//请求异常
            mIView.get().showErrorView(((HttpException) e).message());
        } else if (e instanceof JsonParseException || e instanceof JSONException) {//gson解析异常
            mIView.get().showErrorView("解析异常");
        } else {
            mIView.get().showErrorView("未知异常");
        }

//        baseException.setErrorMsg(StringErrorMsgFactory.getRxErrorMsg(baseException.getErrorCode(),
//                baseException.getHttpcode(), baseException.getErrorMsg()));

    }

    protected abstract void apiError(int code, String errorMsg);//服务器返回的错误

    @Override
    public void onNext(E e) {
        mIView.get().showContentView(null);
        onSuccess(e);
    }

    protected abstract void onSuccess(E e);

    @Override
    public void onCompleted() {

    }


}
