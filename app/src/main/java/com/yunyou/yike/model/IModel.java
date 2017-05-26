package com.yunyou.yike.model;

import com.yunyou.yike.entity.SendCode;
import com.yunyou.yike.entity.User;

import rx.Observable;


/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IModel {


    /**
     * 主页控制器
     */
    interface IHomeFragmentModel {
        void getBanner(AsyCallback callback);//主页碎片 请求轮播图
    }

    /**
     * 发布的订单 控制器
     */
    interface IOrderFragmentModel {
        void getOrder(AsyCallback callback);//获取我发布的订单
    }

    /**
     * 我的碎片 控制器
     */
    interface IMyFragmentModel {
        void getUserInfo(AsyCallback callback);//获取我的个人资料
    }

    /**
     * 我的碎片 控制器
     */
    interface IFeelFragmentModel {
        void getFeelData(AsyCallback callback);//获取朋友圈数据
    }

    /**
     * 找工作的接口model
     */
    interface IJobActivityModel {
        void getJobData(AsyCallback callback);//获取工作列表数据
    }

    /**
     * 注册界面
     */
    interface IRegisterModel {

        Observable<SendCode> sendCode(String phone);//发送验证码

        Observable<User> register(String mobile, String password, String password2, //注册
                                  String code, String time);
    }

    /**
     * model 的回调接口
     */
    interface AsyCallback {
        void startModel(Object o);//请求网络前回调函数

        void noNetWork(Object o);//没有网络连接 回调函数

        void success(Object o);//请求网络回调成功

        void error(Object o);//请求网络回调失败

        void fail(Object o);//请求网络回调错误
    }
}
