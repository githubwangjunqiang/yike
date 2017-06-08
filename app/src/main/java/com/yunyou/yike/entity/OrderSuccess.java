package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/6/7.
 */

public class OrderSuccess {

    /**
     * retcode : 2000
     * msg : 发布成功
     * data : 9
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
