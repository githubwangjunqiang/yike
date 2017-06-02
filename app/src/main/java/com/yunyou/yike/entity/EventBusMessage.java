package com.yunyou.yike.entity;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/5/26.
 */

public class EventBusMessage implements Serializable {
    public static final int LOCATION = 0x1;//定位
    private int msgCode;
    private Object mObject;

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }

    public EventBusMessage(int msgCode, Object object) {
        this.msgCode = msgCode;
        mObject = object;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public EventBusMessage(int msgCode) {
        this.msgCode = msgCode;
    }
}
