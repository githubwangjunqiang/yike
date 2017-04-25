package com.yunyou.yike.Interface;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IPrenester {

    interface IHomeFragmentPrenester extends IPrenester {
        void getBanner();
    }
    interface IAllOrderFragmentPrenester extends IPrenester {
        void getOrder();
        void loodOrder();
    }

}
