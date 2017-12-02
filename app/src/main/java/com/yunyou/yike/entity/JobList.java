package com.yunyou.yike.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/17.
 */

public class JobList implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"3","uid":"18","add_time":"1497339124","work_type":["还有"],"address":"北京市通州区土桥新桥土桥内,欣桥家园东195米","money":"200","rank":"0.0","nickname":"我爸爸","head_pic":"http://120.27.118.19:902/data/upload/app/2017-06-13/2017061313562972.jpg","user_name":"爸"},{"id":"2","uid":"18","add_time":"1497338710","work_type":["还有"],"address":"北京市通州区京塘路辅路北京市海通汽车修理厂南313米","money":"200","rank":"0.0","nickname":"我爸爸","head_pic":"http://120.27.118.19:902/data/upload/app/2017-06-13/2017061313562972.jpg","user_name":"爸"},{"id":"1","uid":"18","add_time":"1497076469","work_type":["规范"],"address":"北京市石景山区八达处路3八大处公园内,映翠湖南75米","money":"2000","rank":"0.0","nickname":"我爸爸","head_pic":"http://120.27.118.19:902/data/upload/app/2017-06-13/2017061313562972.jpg","user_name":"爸"}]
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


    public static class DataBean implements Serializable{
        /**
         * id : 3
         * uid : 18
         * add_time : 1497339124
         * work_type : ["还有"]
         * address : 北京市通州区土桥新桥土桥内,欣桥家园东195米
         * money : 200
         * rank : 0.0
         * nickname : 我爸爸
         * head_pic : http://120.27.118.19:902/data/upload/app/2017-06-13/2017061313562972.jpg
         * user_name : 爸
         */
        private String name;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private String id;
        private String uid;
        private String add_time;
        private String address;
        private String money;
        private String rank;
        private String nickname;
        private String head_pic;
        private String user_name;
        private List<String> work_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public List<String> getWork_type() {
            return work_type;
        }

        public void setWork_type(List<String> work_type) {
            this.work_type = work_type;
        }
    }
}
