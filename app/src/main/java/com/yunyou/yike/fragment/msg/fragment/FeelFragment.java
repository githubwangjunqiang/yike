package com.yunyou.yike.fragment.msg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.baoyz.widget.PullRefreshLayout;
import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.ExListViewAdapter.LoadMoreExListviewAdapter;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.FeelAdapter;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.ui_view.ScrollExpandableListView;

import java.util.List;

public class FeelFragment extends BaseMainFragment implements IView.IFeelFragmentView {

    public static FeelFragment newInstance() {
        FeelFragment fragment = new FeelFragment();
        return fragment;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feel, container, false);
    }

    @Override
    protected IPrenester setIPrenester() {
        return new FeelFragmentPresenter(this);
    }


    private PullRefreshLayout mRefreshLayout;
    private FeelAdapter mFeelAdapter;
    private StateLayout mStateLayout;
    private ScrollExpandableListView mExpandableListView;


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mExpandableListView = (ScrollExpandableListView) viewLayout.findViewById(R.id.feel_exlistview);
        mRefreshLayout = (PullRefreshLayout) viewLayout.findViewById(R.id.feel_layout);
        mStateLayout = (StateLayout) viewLayout.findViewById(R.id.top_statrlayout);
        mStateLayout.setUseAnimation(true);
    }


    @Override
    protected void setlistener() {
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((IPrenester.IFeelFragmentPrenester) mIPrenester).getFeelData(false);
            }
        });
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public void startRefresh(Object object) {
        ((IPrenester.IFeelFragmentPrenester) mIPrenester).getFeelData(true);
    }

    @Override
    public void showLoodingView(Object object) {
        mStateLayout.showLoadingView();
    }

    @Override
    public void showContentView(Object object) {
        mRefreshLayout.setRefreshing(false);
        mStateLayout.showContentView();
    }

    @Override
    public void showErrorView(Object object) {
        mStateLayout.showErrorView();
    }

    @Override
    public void showEmptyView(Object object) {
        mStateLayout.showEmptyView();
    }

    @Override
    public void showNoNetworkView(Object object) {
        mStateLayout.showNoNetworkView();
    }

    @Override
    public void showTimeErrorView(Object object) {
        mStateLayout.showTimeoutView();
    }


    @Override
    public void showFeelData(List<Feel> feelList) {
        if (mFeelAdapter == null) {
            mFeelAdapter = new FeelAdapter(getContext(), feelList, mExpandableListView);
            mFeelAdapter.setOnLoadListener(new LoadMoreExListviewAdapter.OnLoadListener() {
                @Override
                public void onLoadMore() {
                    ((IPrenester.IFeelFragmentPrenester) mIPrenester).loodMoreFeelData();
                }
            });
            mExpandableListView.setAdapter(mFeelAdapter);
        } else {
            mFeelAdapter.setList(feelList);
        }
        for (int i = 0; i < feelList.size(); i++) {
            mExpandableListView.expandGroup(i);
        }
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loodMoreFeelData(List<Feel> feelList) {
        if (mFeelAdapter != null) {
            if (feelList != null && !feelList.isEmpty()) {
                mFeelAdapter.addList(feelList);
            } else {
                mFeelAdapter.setLoosMoreText("-- end --");
            }
        }
    }
}