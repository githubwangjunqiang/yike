package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yunyou.yike.App;
import com.yunyou.yike.AppManager;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.LoadJobAdapter;
import com.yunyou.yike.dagger2.DaggerJobCompcope;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.presenter.JobActivityPresenter;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.util.Map;

import javax.inject.Inject;

public class JobActivity extends BaseMVPActivity<IView.IJobActivityView, JobActivityPresenter>
        implements IView.IJobActivityView {
    private Switch mSwitch;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private RecyclerView mRecyclerView;
    private LoadJobAdapter mLoadJobAdapter;

    @Inject
    JobActivityPresenter mPresenter;
    private int page;

    @Override
    protected JobActivityPresenter mPresenterCreate() {
        DaggerJobCompcope.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mPresenter;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.job_looking_statslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_looking_job;
    }

    @Override
    protected int getRecyerViewLayoutID() {
        return R.id.looking_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSwitch = optionView(R.id.job_switch);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mRecyclerView = optionView(R.id.job_recylerview);
        mPullRefreshLayout = optionView(R.id.looking_layout);
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
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh(false);
            }

            @Override
            public void onLoading() {
                Map<String, String> map = new ArrayMap<>();
                map.put("city_id", AppManager.getInstance().getCityID());
                map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                map.put("page", String.valueOf(page));
                mPresenter.loodMoreJobData(map);
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {
        if (message != null &&
                message.getMsgCode() == EventBusMessage.CONFIRMORDER &&
                message.getObject() != null && message.getObject() instanceof JobList.DataBean) {
            String id = ((JobList.DataBean) message.getObject()).getId();
            if (TextUtils.isEmpty(id)) {
                return;
            }
            Map<String, String> map = new ArrayMap<String, String>();
            map.put("id", id);
            map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
            mPresenter.confirmOrder(map);
        }
    }


    @Override
    public void startRefresh(boolean isShowLoadingView) {
        page = 0;
        if (AppManager.getInstance().getCityID() == null) {
            To.ee("亲获取城市失败，请您重新选择城市试试....");
            return;
        }
        Map<String, String> map = new ArrayMap<>();
        map.put("city_id", AppManager.getInstance().getCityID());
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        map.put("page", String.valueOf(page));
        mPresenter.getJobData(isShowLoadingView, map);
    }


    @Override
    public void showJobData(JobList jobList) {
        page++;
        mPullRefreshLayout.refreshComplete();
        if (mLoadJobAdapter != null) {
            mLoadJobAdapter.recyleList(jobList.getData());
        } else {
            mLoadJobAdapter = new LoadJobAdapter(this, jobList.getData());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mLoadJobAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
            mRecyclerView.setHasFixedSize(true);

        }
    }

    @Override
    public void loodMoreJobData(JobList jobList) {
        page++;
        mPullRefreshLayout.loadMoreComplete();
        if (mLoadJobAdapter != null) {
            mLoadJobAdapter.addList(jobList.getData());
        }
    }



    @Override
    public void confirmOrderSucceess(String string) {
        To.dd(string);
    }
}
