package com.yunyou.yike.ExListViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by ${王俊强} on 2017/4/17.
 */

public abstract class LoadMoreExListviewAdapter<T> extends BaseExpandableListAdapter {
    public static final int ITEM_TYPE_LOAD_MORE_VIEW = 1;//加载中

    private Context mContext;
    private boolean isLoadError = false;//是否加载失败
    private String loosMoreText = "-- 加载中 --";

    public void setLoosMoreText(@NonNull String loosMoreTexts) {
        this.loosMoreText = TextUtils.isEmpty(loosMoreTexts) ? loosMoreText : loosMoreTexts;
        notifyDataSetChanged();
    }

    public Context getmContext() {
        return mContext;
    }

    private LayoutInflater inflater;
    protected AbsListView mAbsListView;


    public LayoutInflater getInflater() {
        return inflater;
    }


    private ListenerScrollListener mLoadMoreScrollListener;

    private List<T> list;

    public List<T> getDatas() {
        return list;
    }

    public void setList(@NonNull List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(@NonNull List<T> listnew) {
        this.list.addAll(listnew);
        notifyDataSetChanged();
        ExpandableListView absListView = (ExpandableListView) mAbsListView;
        for (int i = 0; i < list.size(); i++) {
            absListView.expandGroup(i);
        }
    }


    public LoadMoreExListviewAdapter(@NonNull Context mContext, @NonNull List<T> list, @NonNull AbsListView mAbsListView) {
        this.mContext = mContext;
        this.mAbsListView = mAbsListView;
        this.list = list;
        this.inflater = LayoutInflater.from(mContext);
        mLoadMoreScrollListener = new ListenerScrollListener() {
            @Override
            public void loadMore() {
                if (mOnLoadListener != null) {
                    if (!isLoadError) {
                        mOnLoadListener.onLoadMore();
                    }
                }
            }
        };
        this.mAbsListView.setOnScrollListener(mLoadMoreScrollListener);


    }


    @Override
    public int getGroupCount() {
        return list.size() + 1;
    }




    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition == getGroupCount() - 1) {
            return groupPosition;
        }
        return getGroups(groupPosition);
    }

    protected abstract Object getGroups(int groupPosition);

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == getGroupCount() - 1) {
            return childPosition;
        }
        return getChilds(groupPosition, childPosition);
    }

    protected abstract Object getChilds(int groupPosition, int childPosition);

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        if (groupPosition == getGroupCount() - 1) {
            return ITEM_TYPE_LOAD_MORE_VIEW;
        }
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (getGroupType(groupPosition) == ITEM_TYPE_LOAD_MORE_VIEW) {
            TextView mNoMoreView;
            if (convertView == null) {
                mNoMoreView = new TextView(mContext);
                mNoMoreView.setPadding(20, 20, 20, 20);
                mNoMoreView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                mNoMoreView.setGravity(Gravity.CENTER);
                convertView = mNoMoreView;
            }
            mNoMoreView = (TextView) convertView;
            mNoMoreView.setText(loosMoreText);
        } else {
           return getGroupViews(groupPosition, isExpanded, convertView, parent);
        }
        return convertView;
    }

    protected abstract View getGroupViews(int groupPosition, boolean isExpanded, View convertView,
                                          ViewGroup parent);


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private OnLoadListener mOnLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        void onLoadMore();
    }
}
