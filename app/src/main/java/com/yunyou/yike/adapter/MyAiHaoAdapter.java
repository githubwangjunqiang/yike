package com.yunyou.yike.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.MyAihao;
import com.yunyou.yike.utils.LogUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/19.
 */

public class MyAiHaoAdapter extends MyAdapter<MyAihao> {
    public MyAiHaoAdapter(Context context, List<MyAihao> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        TextView textView;
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.itme_my_aihao, parent,false);
            textView = (TextView) view.findViewById(R.id.itme_tvaihao);
            view.setTag(textView);
        }
        final MyAihao myAihao = getData().get(position);
        textView = (TextView) view.getTag();
        textView.setText(myAihao.getName());
        textView.setSelected(myAihao.isSelecked());
        LogUtils.d(myAihao.isSelecked() + "/" + position);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData().get(position).setSelecked(!myAihao.isSelecked());
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
