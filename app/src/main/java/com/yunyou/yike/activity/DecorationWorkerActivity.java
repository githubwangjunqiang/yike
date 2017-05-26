package com.yunyou.yike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;

public class DecorationWorkerActivity extends BaseActivity {
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_decoration_worker;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = (TextView) optionView(R.id.title_tvtitle);
        mImageViewBack = (ImageView) optionView(R.id.title_ivback);
        mTextViewTitle.setText(R.string.zhuangxiugongren);
        mTextViewTitle.setText(R.string.wanshandingdan);
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
    public void startRefresh(Object object) {

    }
}
