package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.LoadJobAdapter;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.JobActivityPresenter;
import com.yunyou.yike.utils.To;

public class JobActivity extends BaseMVPActivity<IView.IJobActivityView, JobActivityPresenter>
        implements IView.IJobActivityView {
    private Switch mSwitch;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private RecyclerView mRecyclerView;
    private LoadJobAdapter mLoadJobAdapter;

//    @Inject
    JobActivityPresenter mPresenter;

    @Override
    protected JobActivityPresenter mPresenterCreate() {
//        DaggerJobCompcope.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
//                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mPresenter;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.job_looking_statslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.looking_layout;
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
        mTextViewTitle.setText(R.string.zhaogongzuo);
        startRefresh(true);
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
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }


    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }

    @Override
    public void showContentView(Object object) {
        super.showContentView(object);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showJobData(Object jobList) {
//        mJobLists.clear();
//        mJobLists.addAll(jobList);
//        if (mLoadJobAdapter != null) {
//            mLoadJobAdapter.notifyDataSetChanged();
//        } else {
//            mLoadJobAdapter = new LoadJobAdapter(this, mJobLists);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            mRecyclerView.setAdapter(mLoadJobAdapter);
//            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//                    DividerItemDecoration.VERTICAL));
//            mLoadJobAdapter.setOnLoadListener(new LoadMoreWrapper.OnLoadListener() {
//                @Override
//                public void onRetry() {
//                    mPresenter.loodMoreJobData();
//                }
//
//                @Override
//                public void onLoadMore() {
//                    mPresenter.loodMoreJobData();
//                }
//            });
//        }
    }

    @Override
    public void loodMoreJobData(Object jobList) {
//        if (mLoadJobAdapter != null) {
//            mLoadJobAdapter.showLoadSuccess(jobList);
//        }
    }
}
