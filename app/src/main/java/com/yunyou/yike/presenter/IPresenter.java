package com.yunyou.yike.presenter;

/**
 * Created by ${王俊强} on 2017/5/17.
 */

public interface IPresenter {
    /**
     * 主页碎片管理层
     */
    interface IHomeFragmentPrenester extends IPresenter {
        void getBanner();//获取banner 图片
    }

    /**
     * 全部订单界面的 管理者
     */
    interface IAllOrderFragmentPrenester extends IPresenter {
        void getOrder();//获取订单

        void loodOrder();//获取更多订单
    }

    /**
     * 我的界面 管理者
     */
    interface IMyFragmentPrenester extends IPresenter {
        void getUserInfo();//获取我的信息
    }

    /**
     * 感想界面 管理者
     */
    interface IFeelFragmentPrenester extends IPresenter {
        void getFeelData(boolean isShowLoading);//获取感想信息

        void loodMoreFeelData();//获取更多
    }

    /**
     * 找工作 列表
     */
    interface IJobPresenter extends IPresenter {
        void getJobData(boolean isShowLoading);//获取感想信息

        void loodMoreJobData();//获取更多
    }

    /**
     * 订单详情
     */
    interface IOrderInfoPresenter extends IPresenter {
    }

    /**
     * 信息管理
     */
    interface IXinxiGuanliPresenter extends IPresenter {
    }

    /**
     * 我的钱包
     */
    interface IQianBaoPresenter extends IPresenter {
    }

    /**
     * 登陆界面
     */
    interface ILoginPresenter extends IPresenter {
        void login(String mobile,String password,String device_num,String j_du,String w_du,String time);
    }

    /**
     * 注册界面
     */
    interface IRegisterPresenter extends IPresenter {
        void sendCode(String phone);
        void registerUser(String phone,String pas,String pass,String code,String time);
    }
}
