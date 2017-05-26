package com.yunyou.yike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.MyAiHaoAdapter;
import com.yunyou.yike.entity.MyAihao;
import com.yunyou.yike.presenter.XinxiGuanliPresenter;

import java.util.ArrayList;
import java.util.List;

public class XinxiGuanliActivity extends BaseMVPActivity<IView.IxinxiGuanliActivityView, XinxiGuanliPresenter>
        implements IView.IxinxiGuanliActivityView {

    private TextView mTextViewTitle;//标题
    private ImageView mImageViewBack;//返回按钮
    private GridView mGridView;//爱好选择
    private MyAiHaoAdapter mMyAiHaoAdapter;//爱好适配器

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_xinxi_guanli;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mGridView = optionView(R.id.xinxi_gvaihao);
        mTextViewTitle.setText(R.string.xinxiguanli);
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
    }

    @Override
    public void startRefresh(Object object) {
        List<MyAihao> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyAihao("爱好", false));
        }
        mMyAiHaoAdapter = new MyAiHaoAdapter(this, list);
        mGridView.setAdapter(mMyAiHaoAdapter);
    }


    @Override
    protected XinxiGuanliPresenter mPresenterCreate() {
        return new XinxiGuanliPresenter();
    }
}
