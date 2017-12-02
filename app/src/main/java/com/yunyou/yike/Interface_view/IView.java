package com.yunyou.yike.Interface_view;

import com.yunyou.yike.entity.AddressCity;
import com.yunyou.yike.entity.BankList;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.City;
import com.yunyou.yike.entity.CityId;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.entity.HappyList;
import com.yunyou.yike.entity.Jiedan;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.entity.KeFu;
import com.yunyou.yike.entity.Login;
import com.yunyou.yike.entity.Money;
import com.yunyou.yike.entity.MyUserInfo;
import com.yunyou.yike.entity.MyappInfo;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.entity.OrderSuccess;
import com.yunyou.yike.entity.WorkerStyle;
import com.yunyou.yike.entity.WorkerType;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IView {
    void startRefresh(boolean isShowLoadingView);//开始刷新数据

    void onMessageEvent(EventBusMessage message);//接受消息

    void showLoodingView(Object object);//显示等待圈

    void showLoodingDialog(Object object);//显示等待圈

    void showContentView(Object object);//显示主页面

    void showErrorView(Object object);//显示加载失败

    void showEmptyView(Object object);//显示空信息

    void showNoNetworkView(Object object);//显示没有网络

    void showTimeErrorView(Object object);//超时界面

    void showLoginView(Object object);//超时界面

    void ToToast(String string);//显示土司

    void hideDiaLogView();//显示土司

    /**
     * 主页碎片 接口view
     */
    interface IHomeFragmentView extends IView {
        void showBanner(BannerData listBanners);//显示轮播图

        void showCityIDSuccess(Object cityID);//显示城市id
    }

    /**
     * 全部订单 接口 view
     */
    interface IAllOrderFragmentView extends IView {
        void showOrder(Order order);//显示订单

        void showJiedanOrder(Jiedan order);//显示接单订单

        void loodOrder(Order order);//加载显示更多订单

        void loodJiedanOrder(Jiedan order);//加载显示更多接单订单

        void startSuccess(int position);//开始成功

        void canleSuccess(int position);//取消成功
    }

    /**
     * 我的界面 接口view
     */
    interface IMyFragmentView extends IView {
        void showUserInfo(Object userInfo);//显示我的信息

        void showCustomerService(KeFu keFu);//显示客服信息

        void showSuggertions(String string);//显示提交投诉成功
    }

    /**
     * 感想界面接口view
     */
    interface IFeelFragmentView extends IView {
        void showFeelData(List<Feel> feelList);//显示数据

        void loodMoreFeelData(List<Feel> feelList);//现实更多数据
    }

    /**
     * 发布感想界面接口view
     */
    interface ISendFeelActivityView extends IView {
        void showSendSeccess(String string);//发布感想成功
    }

    /**
     * 找工作 接口 view
     */
    interface IJobActivityView extends IView {
        void showJobData(JobList jobList);//显示工作数据

        void loodMoreJobData(JobList jobList);//显示更多工作数据

        void confirmOrderSucceess(String string);//抢单成功


    }

    /**
     * 修改地址界面
     */
    interface IAddressActivityView extends IView {
        void showAddressHotData(AddressCity feelList);//显示热门城市

        void showAddressHotDataError(String string);//显示热门城市失败

        void showAddressCityData(City city);//显示城市列表

        void showAddressCityDataError(String string);//显示城市列表失败
    }

    /**
     * 找工人 发布订单界面
     */
    interface IDecorationWorkerView extends IView {
        void showWorkerTypeSuccess(WorkerType workerType);//显示工种

        void showWorkerStyleSuccess(WorkerStyle workerStyle);//显示风格

        void releaseOrdersSuccess(OrderSuccess orderSuccess);//发布订单成功

        void accidentServerSuccess(WorkerStyle orderSuccess);//显示额外服务

        void showCityIdSuccess(CityId strings);//获取城市id
    }

    /**
     * 银行卡界面
     */
    interface IBankActivityView extends IView {
        void showBankList(BankList bankList);//显示银行卡列表

        void showdefaultBank(String strings);//设置默认卡号成功
    }

    /**
     * 银行卡界面
     */
    interface IAddBankActivityView extends IView {
        void showBankName(String bankName);//显示银行卡名称

        void showBadBankSuccess(String string);//保定成功
    }

    /**
     * 订单详情页
     */
    interface IOrderInfoActivityView extends IView {

    }

    /**
     * 1为最新公告  2服务协议   3平台奖惩   4学习园地   5评分说明
     */
    interface IMyAppInfoActivityView extends IView {
        void showData(MyappInfo myappInfo);//显示信息
    }

    /**
     * 信息管理
     */
    interface IxinxiGuanliActivityView extends IView {
        void showUserinfo(MyUserInfo myUserInfo);//显示用户信息

        void showHappyList(HappyList happyList);//显示好友列表

        void showUnDataSuccess(String success);//修改信息成功

        void showWorkerType(WorkerType workerType);//获取工种信息
    }

    /**
     * 我的钱包
     */
    interface IQianBaoActivityView extends IView {
        void showDataMoney(Money money);//获取我的余额

        void showWithdraw(String strings);//提现成功
    }

    /**
     * 登陆界面
     */
    interface ILoginActivityView extends IView {
        void loginSuccess(Login login);//登陆
    }

    /**
     * 注册界面
     */
    interface IRegisterActivityView extends IView {
        void sendCodeSuccess(Object string);//发送验证码成功

        void sendCodeError(Object string);//发送验证码失败

        void registerSuccess(Object string);//注册成功

        void registerError(Object string);//注册失败
    }
}
