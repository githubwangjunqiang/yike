package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.CityInfo;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.MyAdapter;

import java.util.List;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogMapCity extends Dialog {
    private CallBack mCallBack;
    private ListView mXListView;
    private List<CityInfo> suggestCityList;
    private MyListAdapter mMyListAdapter;
    private String keywords;

    public DiaLogMapCity(Context context, CallBack callBack, List<CityInfo> suggestCityList, String
            keywords) {
        super(context, R.style.CityDialog);
        this.mCallBack = callBack;
        this.keywords = keywords;
        this.suggestCityList = suggestCityList;
        if (suggestCityList == null) {
            dismiss();
            return;
        }
        setContentView(R.layout.dialog_map_city);
        setListener();
        setAdapter();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCallBack != null) {
                    mCallBack.addFeel(suggestCityList.get(position ).city);
                }
                dismiss();
            }
        });
    }


    public interface CallBack {
        void addFeel(String citys);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mXListView = (ListView) findViewById(R.id.dailog_map_city_listview);

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (mMyListAdapter != null) {
            mMyListAdapter.notifyDataSetChanged();
        } else {
            mMyListAdapter = new MyListAdapter(getContext(), suggestCityList, keywords);
            mXListView.setAdapter(mMyListAdapter);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.CityDialog);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private class MyListAdapter extends MyAdapter<CityInfo> {
        private String keyWords;

        public MyListAdapter(Context context, List<CityInfo> data, String keyWords) {
            super(context, data);
            this.keyWords = keyWords;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            MyViewHolder holder;
            if (view == null) {
                holder = new MyViewHolder();
                view = getLayoutInflater().inflate(R.layout.itme_map_citys_listview, parent, false);
                holder.mTextView = (TextView) view.findViewById(R.id.itme_map_city_list_tvname);
                view.setTag(holder);
            }
            holder = (MyViewHolder) view.getTag();
            holder.mTextView.setText(getData().get(position).city + "--" + keyWords);
            return view;
        }

        private class MyViewHolder {
            TextView mTextView;
        }
    }
}
