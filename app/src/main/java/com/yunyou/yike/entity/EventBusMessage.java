package com.yunyou.yike.entity;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/5/26.
 */

public class EventBusMessage implements Serializable {
    /*
    全局定位服务 成功后 发出的消息
     */
    public static final int LOCATION = 0x1;
    /*
     * //接收到 地图选择地址(MapAddressActivity)发出的消息，包含对象（PoiInfo）
     */
    public static final int MAPADDRESS = 0x2;
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
