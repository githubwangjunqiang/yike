package com.yunyou.yike.entity;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/5/27.
 */

public class Login implements Serializable{

    /**
     * retcode : 2000
     * msg : 登录成功！
     * data : {"token":"7de8a7787ee12357142de898ea24b0a6",
     * "user_id":"10","rank":"0.0","head_pic":"","is_listen":"1"}
     */

    private int retcode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * token : 7de8a7787ee12357142de898ea24b0a6
         * user_id : 10
         * rank : 0.0
         * head_pic :
         * is_listen : 1
         */

        private String token;
        private String user_id;
        private String rank;
        private String head_pic;
        private String is_listen;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getIs_listen() {
            return is_listen;
        }

        public void setIs_listen(String is_listen) {
            this.is_listen = is_listen;
        }
    }
}
