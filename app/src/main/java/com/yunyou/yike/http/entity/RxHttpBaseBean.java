package com.yunyou.yike.http.entity;

import com.yunyou.yike.http.cconstant.RxHttpConstant;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/5/22.
 */

public class RxHttpBaseBean<T> implements Serializable {
    private int retcode;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return (retcode == RxHttpConstant.SUCCESS);
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
