package com.yunyou.yike.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.CityList;
import com.yunyou.yike.ui_view.listview.SectionedBaseAdapter;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/1.
 */

public class CityListAdapter extends SectionedBaseAdapter {
    private List<CityList> mCityLists;
    private Context mContext;
    private LayoutInflater mInflater;
    private ListView mListView;

    public CityListAdapter(List<CityList> cityLists, Context context, ListView listView) {
        mCityLists = cityLists;
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        mListView = listView;
    }

    /**
     * 检索关键字
     */
    public void searchKeyWord(String text) {
        if (mCityLists == null || mCityLists.isEmpty() || TextUtils.isEmpty(text)) {
            return;
        }
        int index = 0;
        for (int i = 0; i < mCityLists.size(); i++) {
            index++;
            List<CityList.Data> list = mCityLists.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                index++;
                if (TextUtils.equals(text, list.get(j).getCityName())) {
                    if (mListView != null) {
                        mListView.setSelection(index-2);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public Object getItem(int section, int position) {
        return mCityLists.get(section).getList().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return mCityLists.size();
    }

    @Override
    public int getCountForSection(int section) {
        return mCityLists.get(section).getList().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.header_item_tv, null);
            myViewHolder.mTextView = (TextView) convertView.findViewById(R.id.itme_city_tvname);
            convertView.setTag(myViewHolder);
        }
        myViewHolder = (MyViewHolder) convertView.getTag();
        myViewHolder.mTextView.setText(mCityLists.get(section).getList().get(position).getCityName());

        return convertView;
    }

    private class MyViewHolder {
        TextView mTextView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ViewHolder_D holder_d;
        if (convertView == null) {
            holder_d = new ViewHolder_D();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.header_item, null);
            holder_d.tvname = (TextView) convertView.findViewById(R.id.header_itme_tvname);
            convertView.setTag(holder_d);
        } else {
            holder_d = (ViewHolder_D) convertView.getTag();
        }
        convertView.setClickable(false);
        try {
            holder_d.tvname.setText(mCityLists.get(section).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    /**
     *
     */
    private class ViewHolder_D {
        private TextView tvname;
    }
}
