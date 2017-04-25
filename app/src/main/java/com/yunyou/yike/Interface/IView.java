package com.yunyou.yike.Interface;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public interface IView {
    void startRefresh(Object object);

    interface IHomeFragmentView extends IView {
        void showBanner(List<String> listBanners);
    }
}
