package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/26.
 */

public class Jiedan {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"3","order_sn":"YK764114925883184","user_name":"王五","head_pic":"","money":"250","word_type":["规范"],"address":"的健身房里的开始","time":"1485061234","order_status":"0"},{"id":"4","order_sn":"YK882714925884103","user_name":"李四","head_pic":"","money":"250","word_type":["规范"],"address":"的健身房里的开始","time":"1485061234","order_status":"1"}]
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
         * id : 3
         * order_sn : YK764114925883184
         * user_name : 王五
         * head_pic :
         * money : 250
         * word_type : ["规范"]
         * address : 的健身房里的开始
         * time : 1485061234
         * order_status : 0
         */

        private String id;
        private String order_sn;
        private String user_name;
        private String head_pic;
        private String money;
        private String address;
        private String time;
        private String order_status;
        private List<String> word_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public List<String> getWord_type() {
            return word_type;
        }

        public void setWord_type(List<String> word_type) {
            this.word_type = word_type;
        }
    }
}
