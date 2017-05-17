package com.yunyou.yike.Interface;

import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.entity.Order;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IView {
    void startRefresh(Object object);

    void showLoodingView(Object object);
    void showContentView(Object object);
    void showErrorView(Object object);
    void showEmptyView(Object object);
    void showNoNetworkView(Object object);
    void showTimeErrorView(Object object);

    interface IHomeFragmentView extends IView {
        void showBanner(BannerData listBanners);
    }

    interface IAllOrderFragmentView extends IView {
        void showOrder(List<Order> listBanners);

        void loodOrder(List<Order> listBanners);
    }

    interface IMyFragmentView extends IView {
        void showUserInfo();
    }

    interface IFeelFragmentView extends IView {
        void showFeelData(List<Feel> feelList);
        void loodMoreFeelData(List<Feel> feelList);
    }
}
