package com.yunyou.yike.Interface;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IModel {


    /**
     * 主页控制器
     */
    interface IHomeFragmentModel {
        /**
         * 主页碎片 请求轮播图
         *
         * @param callback
         */
        void getBanner(AsyCallback callback);
    }

    /**
     * 发布的订单 控制器
     */
    interface IOrderFragmentModel {
        /**
         * 获取我发布的订单
         *
         * @param callback
         */
        void getOrder(AsyCallback callback);
    }

    /**
     * 我的碎片 控制器
     */
    interface IMyFragmentModel {
        /**
         * 获取我的个人资料
         *
         * @param callback
         */
        void getUserInfo(AsyCallback callback);
    }
    /**
     * 我的碎片 控制器
     */
    interface IFeelFragmentModel {
        /**
         * 获取朋友圈数据
         *
         * @param callback
         */
        void getFeelData(AsyCallback callback);
    }
    interface AsyCallback {
        /**
         * 请求网络前回调函数
         *
         */
        void startModel(Object o);
        /**
         * 没有网络连接 回调函数
         */
        void noNetWork(Object o);
        /**
         * 请求网络回调成功
         *
         * @param o
         */
        void success(Object o);

        /**
         * 请求网络回调失败
         *
         * @param o
         */
        void error(Object o);

        /**
         * 请求网络回调错误
         *
         * @param o
         */
        void fail(Object o);
    }
}
