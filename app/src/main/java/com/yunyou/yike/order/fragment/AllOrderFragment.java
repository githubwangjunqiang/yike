package com.yunyou.yike.order.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.OrderAdapter;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.order.presenter.ALLOrderFragmentPresenter;
import com.yunyou.yike.utils.LoadMoreScrollListener;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class AllOrderFragment extends BaseMainFragment implements IView.IAllOrderFragmentView {


    public static AllOrderFragment newInstance() {
        AllOrderFragment fragment = new AllOrderFragment();
        return fragment;
    }

    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private OrderAdapter mOrderAdapter;

    @Override
    protected void setlistener() {
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh(null);
            }
        });
        mRecyclerView.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                ((IPrenester.IAllOrderFragmentPrenester) mIPrenester).loodOrder();
            }
        });
    }

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mRefreshLayout = (PullRefreshLayout) viewLayout.findViewById(R.id.all_order_layout);
        mRecyclerView = (RecyclerView) viewLayout.findViewById(R.id.all_order_recyleview);
        mOrderAdapter = new OrderAdapter(null, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mOrderAdapter);
    }

    @Override
    protected IPrenester setIPrenester() {
        return new ALLOrderFragmentPresenter(this);
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_order, container, false);
    }

    @Override
    public void startRefresh(Object object) {
        mRefreshLayout.setRefreshing(true);
        ((IPrenester.IAllOrderFragmentPrenester) mIPrenester).getOrder();
    }

    @Override
    public void showOrder(List<Order> listBanners) {
        mRefreshLayout.setRefreshing(false);
        mOrderAdapter.setList(listBanners);
    }

    @Override
    public void loodOrder(List<Order> listBanners) {
        mOrderAdapter.loodList(listBanners);
    }
}
