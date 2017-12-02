package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/9.
 */

public class HappyList {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"58","name":"游泳"},{"id":"28","name":"鬼金融股"},{"id":"37","name":"研讨会更多"},{"id":"85","name":"装潢"}]
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
         * id : 58
         * name : 游泳
         */

        private String id;
        private String name;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
