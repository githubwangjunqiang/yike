package com.yunyou.yike.Interface;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IModel {
    interface AsyCallback {
        void success(Object o);

        void error(Object o);

        void fail(Object o);
    }

    interface IHomeFragmentModel {
        void getBanner(AsyCallback callback);
    }

}
