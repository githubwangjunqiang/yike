package com.yunyou.yike.adapter;

import android.content.Context;
import android.view.View;

import com.angel.adapter.SWRecyclerAdapter;
import com.angel.adapter.SWViewHolder;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.MyappInfo;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/19.
 */

public class MyAppInfoAdapter extends SWRecyclerAdapter<MyappInfo.DataBean> {
    public MyAppInfoAdapter(Context context, List<MyappInfo.DataBean> list) {
        super(context, list);
    }

    @Override
    protected void setAutoUtilsView(View view) {
        AutoUtils.auto(view);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.itme_rcyleview_myappinfo;
    }

    @Override
    public void bindData(SWViewHolder holder, int position, MyappInfo.DataBean item) {
        String post_title = item.getPost_title();
        holder.setText(R.id.itme_myapp_tvtitle, "标题：" + post_title);
        String post_modified = item.getPost_modified();
        holder.setText(R.id.itme_myapp_tvtime, "添加时间：" + post_modified);
        holder.setText(R.id.itme_myapp_tvcontent, "内容详情：" + item.getPost_content());
    }
}
