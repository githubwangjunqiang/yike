package com.yunyou.yike.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.AddressCity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/1.
 */

public class AddressHotAdapter extends MyAdapter<AddressCity.DataBean> {
    public AddressHotAdapter(Context context, List<AddressCity.DataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.itme_hot_city, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.itme_hotcity_tvname);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        try {
            final AddressCity.DataBean dataBean = getData().get(position);
            holder.mTextView.setText(dataBean.getName());
            holder.mTextView.setSelected(dataBean.isSelecked());
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()) {
                        return;
                    }
                    for (int i = 0; i < getData().size(); i++) {
                        getData().get(i).setSelecked(false);
                    }
                    getData().get(position).setSelecked(true);
                    notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView mTextView;
    }
}
