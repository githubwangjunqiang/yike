package com.yunyou.yike.entity;

import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.http.entity.RxBaseBean;

/**
 * Created by ${王俊强} on 2017/5/22.
 */

public class SendCode extends RxBaseBean {

    /**
     * retcode : 2000
     * msg : 验证码已发送到您手机，请查收！
     * data : 您的验证码是：【564882】。请不要把验证码泄露给其他人。
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

    @Override
    public boolean isSuccess() {
        return retcode == RxHttpConstant.SUCCESS;
    }
}
