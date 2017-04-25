package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class BannerData {

    /**
     * status : 1
     * msg : 获取成功
     * result : ["admin/20170418/58f572bb49af2.jpg","admin/20170418/58f572f433d6b.jpg","admin/20170418/58f572fec33ed.jpg"]
     */

    private int status;
    private String msg;
    private List<String> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
