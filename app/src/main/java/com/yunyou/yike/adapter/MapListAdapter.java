package com.yunyou.yike.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.yunyou.yike.R;
import com.yunyou.yike.utils.Text_Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/3.
 */

public class MapListAdapter extends RecyclerView.Adapter<MapViewHolder> {
    private List<PoiInfo> mList;
    private Context mContext;
    private CallBack mCallBack;
    private boolean loading = false;

    public interface CallBack {
        void onClick(LatLng latLng);

        void onSuccess(PoiInfo latLng);
    }

    public MapListAdapter(List<PoiInfo> list, Context context, CallBack callBack) {
        mCallBack = callBack;
        mList = new ArrayList<>();
        mList.addAll(list);
        mContext = context;
    }

    /**
     * 刷新数据
     */
    public void setList(List<PoiInfo> list) {
        mList.clear();
        mList.addAll(list);
        loading = false;
        notifyDataSetChanged();
    }

    /**
     * 显示Loading
     */
    public void showLoading() {
        loading = true;
        notifyDataSetChanged();
    }

    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MapViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.itme_map_listview, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, int position) {
        if (loading) {
            holder.mTextView.setVisibility(View.INVISIBLE);
            holder.mProgressBar.setVisibility(View.VISIBLE);
            holder.mTextView.setClickable(false);
            return;
        }
        holder.mTextView.setClickable(true);
        holder.mTextView.setVisibility(View.VISIBLE);
        holder.mProgressBar.setVisibility(View.GONE);
        holder.mTextViewIndex.setText((position + 1) + "");
        final PoiInfo poiInfo = mList.get(position);
        if (position == 0) {
            holder.mTextView.setSelected(true);
            String city = poiInfo.city;
            String text = poiInfo.address;
            String string = city + "\n" + text;
            Text_Size.setSize(holder.mTextView, string, 0, city.length(),
                    "#E13120", 14, city.length(), string.length(), "#707070", 13);
            if (mCallBack != null) {
                mCallBack.onSuccess(poiInfo);
            }

        } else {
            holder.mTextView.setSelected(false);
            String name = poiInfo.name;
            String city = poiInfo.address;
            String string = name + "\n" + city;
            Text_Size.setSize(holder.mTextView, string, 0, name.length(),
                    "#3c3b3b", 14, name.length(), string.length(), "#707070", 13);
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) {
                        mCallBack.onClick(poiInfo.location);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class MapViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;
    TextView mTextViewIndex;
    ProgressBar mProgressBar;

    public MapViewHolder(View itemView, Context context) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.itme_maplistview_tv);
        mTextViewIndex = (TextView) itemView.findViewById(R.id.itme_map_lkist_tv);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.itme_loading);
    }
}
