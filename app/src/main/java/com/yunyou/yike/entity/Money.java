package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/6/9.
 */

public class Money {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : 0.00
     */

    private int retcode;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
