package com.yunyou.yike.presenter;

import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.model.IModel;
import com.yunyou.yike.model.OrderFragmentModel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class ALLOrderFragmentPresenter extends BasePresenter<IView.IAllOrderFragmentView>
        implements IPresenter.IAllOrderFragmentPrenester {
    private IModel.IOrderFragmentModel mModel;

    public ALLOrderFragmentPresenter() {
        mModel = new OrderFragmentModel();

    }

    @Override
    public void getOrder() {
        mModel.getOrder(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {

            }

            @Override
            public void noNetWork(Object o) {

            }

            @Override
            public void success(Object o) {
                getView().showOrder((List<Order>) o);
            }

            @Override
            public void error(Object o) {

            }

            @Override
            public void fail(Object o) {

            }
        });
    }

    @Override
    public void loodOrder() {
        mModel.getOrder(new IModel.AsyCallback() {
            @Override
            public void startModel(Object o) {

            }

            @Override
            public void noNetWork(Object o) {

            }

            @Override
            public void success(Object o) {
                getView().loodOrder((List<Order>) o);
            }

            @Override
            public void error(Object o) {

            }

            @Override
            public void fail(Object o) {

            }
        });
    }
}
