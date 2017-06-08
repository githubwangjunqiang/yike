package com.yunyou.yike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;

public class LookingWorkersActivity extends BaseActivity {
    private ImageView mImageViewBack;//返回按钮
    private TextView mTextViewTitle;//title


    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_looking_workers;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mTextViewTitle.setText(R.string.lookingworker);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    /**
     * 监听器
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.looking_worker_ivzhuang://装修工人
                DecorationWorkerActivity.startDecorationWorkerActivity(this, "1");
                break;
            case R.id.looking_worker_ivjian://建筑工人
                DecorationWorkerActivity.startDecorationWorkerActivity(this, "2");
                break;
            case R.id.looking_worker_ivanzhuang://安装工人
                DecorationWorkerActivity.startDecorationWorkerActivity(this, "3");
                break;
            case R.id.looking_worker_ivtuandui://团队
                DecorationWorkerActivity.startDecorationWorkerActivity(this, "4");
                break;
            default:
                break;
        }
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }
}
