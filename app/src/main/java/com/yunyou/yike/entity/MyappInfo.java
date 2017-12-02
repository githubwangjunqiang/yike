package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/16.
 */

public class MyappInfo {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"7","post_title":"吞吞吐吐拖拖拖拖拖","post_content":"解决解决军军军军军军军军军军军军军军军军军军军军军","post_modified":"2017-04-18 15:15:39"}]
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
         * id : 7
         * post_title : 吞吞吐吐拖拖拖拖拖
         * post_content : 解决解决军军军军军军军军军军军军军军军军军军军军军
         * post_modified : 2017-04-18 15:15:39
         */

        private String id;
        private String post_title;
        private String post_content;
        private String post_modified;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_content() {
            return post_content;
        }

        public void setPost_content(String post_content) {
            this.post_content = post_content;
        }

        public String getPost_modified() {
            return post_modified;
        }

        public void setPost_modified(String post_modified) {
            this.post_modified = post_modified;
        }
    }
}
