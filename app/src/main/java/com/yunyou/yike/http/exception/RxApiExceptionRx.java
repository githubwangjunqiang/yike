package com.yunyou.yike.http.exception;

/**
 * Created by ${王俊强} on 2017/5/24.
 */

public class RxApiExceptionRx extends RxBaseException {

    public RxApiExceptionRx(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public RxApiExceptionRx() {
    }
}
