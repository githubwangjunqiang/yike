package com.yunyou.yike.http.rx;

import com.google.gson.JsonParseException;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.exception.RxApiExceptionRx;
import com.yunyou.yike.http.exception.RxBaseException;

import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by ${王俊强} on 2017/5/24.
 */

public abstract class RxExceptionSubscriber<E> extends RxBaseSubscriber<E> {
    private WeakReference<IView> mIView;

    public RxExceptionSubscriber(WeakReference<IView> IView) {
        mIView = IView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mIView.get().showLoodingDialog(null);
    }


    @Override
    public void onError(Throwable e) {
        RxBaseException baseException = new RxBaseException();


        if (e instanceof RxApiExceptionRx) {//是我们自己定义的异常

//            baseException.setErrorCode(RxBaseException.API_ERROR);
//            baseException.setHttpcode(((RxApiExceptionRx) e).getErrorCode());
//            baseException.setErrorMsg(((RxApiExceptionRx) e).getErrorMsg());
            apiError(((RxApiExceptionRx) e).getErrorMsg());

        } else if (e instanceof SocketException) {//网络异常
//            baseException.setErrorCode(RxBaseException.SOCKET_NO_ERROR);
//            noNetWorkError();

            mIView.get().showNoNetworkView(null);
        } else if (e instanceof SocketTimeoutException) {//网络超时

//            baseException.setErrorCode(RxBaseException.SOCKET_TIMEOUT_ERROR);
//            timeOut();
            mIView.get().showTimeErrorView(null);
        } else if (e instanceof HttpException) {//请求异常

//            baseException.setErrorCode(RxBaseException.HTTP_ERROR);
//            baseException.setHttpcode(((HttpException) e).code());
//            baseException.setErrorMsg(((HttpException) e).message());
//            httpError(((HttpException) e).message());
            mIView.get().showErrorView(((HttpException) e).message());
        } else if (e instanceof JsonParseException) {//gson解析异常

//            baseException.setErrorCode(RxBaseException.JSON_ERROR);
//            jsonParseError();
            mIView.get().showErrorView("解析异常");
        } else {

//            baseException.setErrorCode(RxBaseException.UNKNOPWN_ERROR);
//            noKownError();
            mIView.get().showErrorView("未知异常");
        }

        baseException.setErrorMsg(StringErrorMsgFactory.getRxErrorMsg(baseException.getErrorCode(),
                baseException.getHttpcode(), baseException.getErrorMsg()));

    }

    //    protected abstract void noKownError();//未知异常
//
//    protected abstract void jsonParseError();//解析异常
//
//    protected abstract void httpError(String message);//接口返回错误
//
//    protected abstract void timeOut();//网络超时
//
//    protected abstract void noNetWorkError();//没有网络连接
//
    protected abstract void apiError(String errorMsg);//服务器返回的错误

    @Override
    public void onCompleted() {

    }


}
