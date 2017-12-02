package com.yunyou.yike.fragment.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPFragment;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.OrderAdapter;
import com.yunyou.yike.adapter.OrderJiedanAdapter;
import com.yunyou.yike.dagger2.DaggerAllOrderCompcoet;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Jiedan;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.presenter.ALLOrderFragmentPresenter;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class CancelOrderFragment extends BaseMVPFragment<IView.IAllOrderFragmentView,
        ALLOrderFragmentPresenter>
        implements IView.IAllOrderFragmentView {
    @Inject
    ALLOrderFragmentPresenter mALLOrderFragmentPresenter;
    private static final String STATE = "state";
    private static final String STATECODE = "3";
    private int page = 0;

    private static final String PARAMS = "params";
    private boolean myfadan;
    public static CancelOrderFragment newInstance(boolean isMyFadan) {
        CancelOrderFragment fragment = new CancelOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PARAMS, isMyFadan);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            myfadan = getArguments().getBoolean(PARAMS);
        }
    }
    private OrderJiedanAdapter mOrderJiedanAdapter;
    @Override
    protected int getRecyerViewID() {
        return R.id.all_order_layout;
    }

    private RecyclerView mRecyclerView;
    private OrderAdapter mOrderAdapter;

    @Override
    protected void setlistener() {
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               startRefresh(false);
            }

            @Override
            public void onLoading() {
                Map<String, String> map = new ArrayMap();
                map.put(STATE, STATECODE);
                map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                map.put("page", page + "");
                if (myfadan) {
                    mALLOrderFragmentPresenter.loodMyjieOrder(map);
                } else {
                    mALLOrderFragmentPresenter.loodOrder(map);
                }
            }
        });
    }

    @Override
    protected void RogerMessage(EventBusMessage message) {

    }

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mRecyclerView = optainView(R.id.all_order_recyleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new LandingAnimator());
        mRecyclerView.setHasFixedSize(true);
    }


    @Override
    protected int getStateLayoutID() {
        return R.id.orderall_starlayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_order, container, false);
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        page = 0;
        Map<String, String> map = new ArrayMap();
        map.put(STATE, STATECODE);
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        map.put("page", page + "");
        if (myfadan) {
            mALLOrderFragmentPresenter.getMyJiedanOrder(map, isShowLoadingView);
        } else {
            mALLOrderFragmentPresenter.getOrder(map, isShowLoadingView);
        }
    }

    /**
     * 设置适配器
     *
     * @param list
     */
    private void setAdapter(List<Order.DataBean> list) {
        page++;
        if (mOrderAdapter != null) {
            mOrderAdapter.recyleList(list);
        } else {
            mOrderAdapter = new OrderAdapter(this.getContext(), list, new OrderAdapter.Callback() {
                @Override
                public void onClickStart(Order.DataBean dataBean, int position) {
                    Map<String, String> map = new ArrayMap<>();
                    map.put("id", dataBean.getId());
                    map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                    mALLOrderFragmentPresenter.workStart(map, position);
                }

                @Override
                public void onClickCanle(Order.DataBean dataBean, int position) {
                    Map<String, String> map = new ArrayMap<>();
                    map.put("id", dataBean.getId());
                    map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                    mALLOrderFragmentPresenter.workCancel(map, position);
                }

            });
            mRecyclerView.setAdapter(mOrderAdapter);
        }
    }

    @Override
    public void showOrder(Order listBanners) {
        setAdapter(listBanners.getData());
    }

    @Override
    public void showJiedanOrder(Jiedan order) {
        setJieDanAdapter(order.getData());
    }
    /**
     * 适配接单的数据
     */
    private void setJieDanAdapter(List<Jiedan.DataBean> list) {
        page++;
        if (mOrderJiedanAdapter != null) {
            mOrderJiedanAdapter.recyleList(list);
        } else {
            mOrderJiedanAdapter = new OrderJiedanAdapter(this.getContext(), list,
                    new OrderJiedanAdapter.Callback() {
                        @Override
                        public void onClickStart(Jiedan.DataBean dataBean, int position) {
                            Map<String, String> map = new ArrayMap<>();
                            map.put("id", dataBean.getId());
                            map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                            mALLOrderFragmentPresenter.workStart(map, position);
                        }

                        @Override
                        public void onClickCanle(Jiedan.DataBean dataBean, int position) {
                            Map<String, String> map = new ArrayMap<>();
                            map.put("id", dataBean.getId());
                            map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                            mALLOrderFragmentPresenter.workCancel(map, position);
                        }

                    });
            mRecyclerView.setAdapter(mOrderJiedanAdapter);
        }
    }
    @Override
    public void loodOrder(Order listBanners) {
        page++;
        if (mOrderAdapter != null) {
            mOrderAdapter.addList(listBanners.getData());
        }
    }

    @Override
    public void loodJiedanOrder(Jiedan order) {
        page++;
        if (mOrderJiedanAdapter != null) {
            mOrderJiedanAdapter.addList(order.getData());
        }
    }

    @Override
    protected ALLOrderFragmentPresenter mPresenterCreate() {
        DaggerAllOrderCompcoet.builder()
                .appCompcoent(((App) getActivity().getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule())
                .build().inject(this);
        return mALLOrderFragmentPresenter;
    }
    @Override
    public void startSuccess(int position) {
        To.ss(mRecyclerView, "成功");
        mOrderAdapter.getList().get(position).setStatus("1");
        mOrderAdapter.notifyItemChanged(position);
    }

    @Override
    public void canleSuccess(int position) {
        To.ss(mRecyclerView, "成功");
        mOrderAdapter.getList().get(position).setStatus("3");
        mOrderAdapter.notifyItemChanged(position);
    }
}
