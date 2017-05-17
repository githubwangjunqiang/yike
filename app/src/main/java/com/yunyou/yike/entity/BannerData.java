package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class BannerData {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"slide_pic":"admin/20170418/58f572bb49af2.jpg","slide_url":""},{"slide_pic":"admin/20170418/58f572f433d6b.jpg","slide_url":""},{"slide_pic":"admin/20170418/58f572fec33ed.jpg","slide_url":""}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * slide_pic : admin/20170418/58f572bb49af2.jpg
         * slide_url :
         */

        private String slide_pic;
        private String slide_url;

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }
    }
}
