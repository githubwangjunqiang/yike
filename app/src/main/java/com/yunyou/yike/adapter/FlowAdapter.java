package com.yunyou.yike.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.HappyList;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class FlowAdapter extends TagAdapter<HappyList.DataBean> {
    private Context context;

    public FlowAdapter(List<HappyList.DataBean> datas, Context context) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, HappyList.DataBean s) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flowlayout_new, null);
        tv.setText(s.getName() + "");
        return tv;
    }

    @Override
    public boolean setSelected(int position, HappyList.DataBean s) {
        return super.setSelected(position, s);
    }
}
