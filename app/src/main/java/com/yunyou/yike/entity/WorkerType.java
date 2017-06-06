package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/5.
 */

public class WorkerType {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"1","name":"个梵蒂冈"},{"id":"2","name":"机会"},{"id":"3","name":"苦苦体验"},{"id":"4","name":"吃不饱"},{"id":"5","name":"水电费水电费"},{"id":"8","name":"而我却二"}]
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
         * id : 1
         * name : 个梵蒂冈
         */

        private String id;
        private String name;

        public DataBean() {
        }

        public DataBean(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public DataBean(String name) {
            this.name = name;
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
