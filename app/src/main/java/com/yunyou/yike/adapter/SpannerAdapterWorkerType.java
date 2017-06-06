package com.yunyou.yike.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.WorkerType;

import java.util.List;


/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class SpannerAdapterWorkerType extends BaseAdapter {
    private Context context;
    private List<WorkerType.DataBean> list;

    public SpannerAdapterWorkerType(Context context, List<WorkerType.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        TextView textView = new TextView(context);
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        textView.setPadding(20, 38, 20, 38);
//        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        textView.setTextSize(13);
        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundResource(R.drawable.edittext_biankuang);
        textView.setText(list.get(position).getName()+"");
        return textView;
    }
}
