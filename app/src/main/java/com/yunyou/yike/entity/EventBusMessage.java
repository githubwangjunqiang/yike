package com.yunyou.yike.entity;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/5/26.
 */

public class EventBusMessage implements Serializable {
    /*
    全局定位服务 成功后 发出的消息 不包含任何数据对象
     */
    public static final int LOCATION = 0x1;
    /*
     * //接收到 地图选择地址(MapAddressActivity)发出的消息，包含对象（MyAddress）
     */
    public static final int MAPADDRESS = 0x2;
    /*
     * 接收到 融云发布的token失效的消息 不包含任何数据对象
     */
    public static final int RONGIMTOKENINCORRECT = 0x3;
    /*
     * token 过期 登陆后 发送登陆成功的消息 不包含任何信息对象
     */
    public static final int TOKENLOGIN = 0x4;
    /*
     * 找工作列表 点击抢单 发送消息 携带  JobList.DataBean data  数据对象
     */
    public static final int CONFIRMORDER = 0x5;




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
