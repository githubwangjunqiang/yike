package com.yunyou.yike.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/31.
 */

public class AddressCity implements Serializable {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"52","name":"北京"},{"id":"53","name":"福州"},{"id":"55","name":"南平"},{"id":"264","name":"呼伦贝尔"},{"id":"270","name":"银川"},{"id":"284","name":"青岛"},{"id":"289","name":"济宁"},{"id":"294","name":"泰安"},{"id":"296","name":"潍坊"},{"id":"297","name":"烟台"},{"id":"302","name":"大同"},{"id":"321","name":"上海"},{"id":"343","name":"天津"}]
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
         * id : 52
         * name : 北京
         */

        private String id;
        private String name;
        private boolean selecked;

        public boolean isSelecked() {
            return selecked;
        }

        public void setSelecked(boolean selecked) {
            this.selecked = selecked;
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
