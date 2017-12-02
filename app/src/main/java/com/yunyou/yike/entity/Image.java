package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/6/13.
 */

public class Image {

    /**
     * retcode : 2000
     * msg : 上传成功
     * data : /./Uploads/APP/2017-06-13/20170613133251231.jpg
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
