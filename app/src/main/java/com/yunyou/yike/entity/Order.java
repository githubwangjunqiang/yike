package com.yunyou.yike.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class Order implements Parcelable {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"4","order_sn":"YK882714925884103","user_name":"张三","head_pic":"","money":"250","order_status":"1","word_type":["规范"],"address":"的健身房里的开始","time":"1485061234","status":"3"}]
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

    public static class DataBean implements Parcelable {
        /**
         * id : 4
         * order_sn : YK882714925884103
         * user_name : 张三
         * head_pic :
         * money : 250
         * order_status : 1
         * word_type : ["规范"]
         * address : 的健身房里的开始
         * time : 1485061234
         * status : 3
         */

        private String id;
        private String order_sn;
        private String user_name;
        private String head_pic;
        private String money;
        private String order_status;
        private String address;
        private String time;
        private String status;
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

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getWord_type() {
            return word_type;
        }

        public void setWord_type(List<String> word_type) {
            this.word_type = word_type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.order_sn);
            dest.writeString(this.user_name);
            dest.writeString(this.head_pic);
            dest.writeString(this.money);
            dest.writeString(this.order_status);
            dest.writeString(this.address);
            dest.writeString(this.time);
            dest.writeString(this.status);
            dest.writeStringList(this.word_type);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.order_sn = in.readString();
            this.user_name = in.readString();
            this.head_pic = in.readString();
            this.money = in.readString();
            this.order_status = in.readString();
            this.address = in.readString();
            this.time = in.readString();
            this.status = in.readString();
            this.word_type = in.createStringArrayList();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.retcode);
        dest.writeString(this.msg);
        dest.writeTypedList(this.data);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.retcode = in.readInt();
        this.msg = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
