package com.yunyou.yike.presenter;

import com.google.gson.Gson;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.http.entity.RxApi;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class FeelFragmentPresenter extends BasePresenter<IView.IFeelFragmentView>
        implements IPresenter.IFeelFragmentPrenester {

    public FeelFragmentPresenter(RxApi mApi, Gson mGson) {
        super(mApi, mGson);
    }

    @Override
    public void getFeelData(final boolean isShowLoading) {
    }

    @Override
    public void loodMoreFeelData() {
    }
}
