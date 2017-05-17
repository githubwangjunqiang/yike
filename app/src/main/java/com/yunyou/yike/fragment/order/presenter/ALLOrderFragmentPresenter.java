package com.yunyou.yike.fragment.order.presenter;

import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.fragment.order.OrderFragmentModel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class ALLOrderFragmentPresenter implements IPrenester.IAllOrderFragmentPrenester {
    private IView.IAllOrderFragmentView mIView;
    private IModel.IOrderFragmentModel mModel;

    public ALLOrderFragmentPresenter(IView.IAllOrderFragmentView iView) {
        mIView = iView;
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
                if(o instanceof List){
                    mIView.showOrder((List<Order>) o);
                }
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
                if(o instanceof List){
                    mIView.loodOrder((List<Order>) o);
                }
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
