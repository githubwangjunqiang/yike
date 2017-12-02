package com.yunyou.yike.fragment.msg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.yunyou.yike.BaseMVPFragment;
import com.yunyou.yike.ExListViewAdapter.LoadMoreExListviewAdapter;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.FeelAdapter;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.presenter.FeelFragmentPresenter;
import com.yunyou.yike.ui_view.ScrollExpandableListView;

import java.util.List;

public class FeelFragment extends BaseMVPFragment<IView.IFeelFragmentView, FeelFragmentPresenter>
        implements IView.IFeelFragmentView {

    public static FeelFragment newInstance() {
        FeelFragment fragment = new FeelFragment();
        return fragment;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.top_statrlayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.feel_layout;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feel, container, false);
    }


    private FeelAdapter mFeelAdapter;
    private ScrollExpandableListView mExpandableListView;


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mExpandableListView = (ScrollExpandableListView) viewLayout.findViewById(R.id.feel_exlistview);
    }


    @Override
    protected void setlistener() {
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    protected void RogerMessage(EventBusMessage message) {

    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
    }





    @Override
    public void showFeelData(List<Feel> feelList) {
        if (mFeelAdapter == null) {
            mFeelAdapter = new FeelAdapter(getContext(), feelList, mExpandableListView);
            mFeelAdapter.setOnLoadListener(new LoadMoreExListviewAdapter.OnLoadListener() {
                @Override
                public void onLoadMore() {
                }
            });
            mExpandableListView.setAdapter(mFeelAdapter);
        } else {
            mFeelAdapter.setList(feelList);
        }
        for (int i = 0; i < feelList.size(); i++) {
            mExpandableListView.expandGroup(i);
        }
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

    @Override
    protected FeelFragmentPresenter mPresenterCreate() {
        return new FeelFragmentPresenter(null,null);
    }
}