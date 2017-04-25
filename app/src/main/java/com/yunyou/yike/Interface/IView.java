package com.yunyou.yike.Interface;

import com.yunyou.yike.entity.Order;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IView {
    void startRefresh(Object object);

    interface IHomeFragmentView extends IView {
        void showBanner(List<String> listBanners);
    }
    interface IAllOrderFragmentView extends IView {
        void showOrder(List<Order> listBanners);
        void loodOrder(List<Order> listBanners);
    }
}
