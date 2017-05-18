package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.LoadJobAdapter;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.presenter.IJobActivityPresenter;
import com.yunyou.yike.recyleviewadapter.LoadMoreWrapper;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.List;

public class JobActivity extends BaseMVPActivity<IView.IJobActivityView, IJobActivityPresenter>
        implements IView.IJobActivityView {
    private Switch mSwitch;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private RecyclerView mRecyclerView;
    private LoadJobAdapter mLoadJobAdapter;
    private StateLayout mStateLayout;
    private PullRefreshLayout mRefreshLayout;
    private List<JobList> mJobLists;


    @Override
    protected IJobActivityPresenter mPresenterCreate() {
        return new IJobActivityPresenter();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_looking_job;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSwitch = optionView(R.id.job_switch);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mRecyclerView = optionView(R.id.job_recylerview);
        mStateLayout = optionView(R.id.job_looking_statslayout);
        mRefreshLayout = optionView(R.id.looking_layout);
        mTextViewTitle.setText(R.string.zhaogongzuo);
        mJobLists = new ArrayList<>();
        mStateLayout.setUseAnimation(true);
        startRefresh(null);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                To.oo("调试成功" + isChecked);
            }
        });
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getJobData(false);
            }
        });
    }

    @Override
    public void startRefresh(Object object) {
        mPresenter.getJobData(true);
    }

    @Override
    public void showLoodingView(Object object) {
        mStateLayout.showLoadingView();
    }

    @Override
    public void showContentView(Object object) {
        mStateLayout.showContentView();
        mRefreshLayout.setRefreshing(false);
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
    public void showJobData(List<JobList> jobList) {
        mJobLists.clear();
        mJobLists.addAll(jobList);
        if (mLoadJobAdapter != null) {
            mLoadJobAdapter.notifyDataSetChanged();
        } else {
            mLoadJobAdapter = new LoadJobAdapter(this, mJobLists);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mLoadJobAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            mLoadJobAdapter.setOnLoadListener(new LoadMoreWrapper.OnLoadListener() {
                @Override
                public void onRetry() {
                    mPresenter.loodMoreJobData();
                }

                @Override
                public void onLoadMore() {
                    mPresenter.loodMoreJobData();
                }
            });
        }
    }

    @Override
    public void loodMoreJobData(List<JobList> jobList) {
        if (mLoadJobAdapter != null) {
            mLoadJobAdapter.showLoadSuccess(jobList);
        }
    }
}
