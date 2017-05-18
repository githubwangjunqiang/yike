package com.yunyou.yike.Interface_view;

import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.entity.Order;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IView {
    void startRefresh(Object object);//开始刷新数据

    void showLoodingView(Object object);//显示等待圈

    void showContentView(Object object);//显示主页面

    void showErrorView(Object object);//显示加载失败

    void showEmptyView(Object object);//显示空信息

    void showNoNetworkView(Object object);//显示没有网络

    void showTimeErrorView(Object object);//超时界面

    /**
     * 主页碎片 接口view
     */
    interface IHomeFragmentView extends IView {
        void showBanner(BannerData listBanners);//显示轮播图
    }

    /**
     * 全部订单 接口 view
     */
    interface IAllOrderFragmentView extends IView {
        void showOrder(List<Order> listBanners);//显示订单

        void loodOrder(List<Order> listBanners);//加载显示更多订单
    }

    /**
     * 我的界面 接口view
     */
    interface IMyFragmentView extends IView {
        void showUserInfo();//显示我的信息
    }

    /**
     * 感想界面接口view
     */
    interface IFeelFragmentView extends IView {
        void showFeelData(List<Feel> feelList);//显示数据

        void loodMoreFeelData(List<Feel> feelList);//现实更多数据
    }

    /**
     * 找工作 接口 view
     */
    interface IJobActivityView extends IView {
        void showJobData(List<JobList> feelList);//显示工作数据

        void loodMoreJobData(List<JobList> feelList);//现实更多工作数据
    }

    /**
     * 订单详情页
     */
    interface IOrderInfoActivityView extends IView{

    }
    /**
     * 信息管理
     */
    interface IxinxiGuanliActivityView extends IView{

    }
}
