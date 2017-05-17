package com.yunyou.yike.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.ExListViewAdapter.LoadMoreExListviewAdapter;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.Feel;
import com.yunyou.yike.ui_view.dialog.DialogEtidingFeel;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/26.
 */

public class FeelAdapter extends LoadMoreExListviewAdapter<Feel> {


    public FeelAdapter(@NonNull Context mContext, @NonNull List<Feel> list, @NonNull AbsListView mAbsListView) {
        super(mContext, list, mAbsListView);
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return getDatas().get(groupPosition).getList().size() + 1;
    }

    @Override
    protected Object getGroups(int groupPosition) {
        return getDatas().get(groupPosition);
    }

    @Override
    protected Object getChilds(int groupPosition, int childPosition) {
        return getDatas().get(groupPosition).getList().get(childPosition);
    }

    @Override
    protected View getGroupViews(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GViewHolder holder;
        if (convertView == null) {
            holder = new GViewHolder();
            convertView = getInflater().inflate(R.layout.itme_feel_recyleview, parent, false);
            holder.mDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.itme_feel_iviamge);
            holder.mTvName = (TextView) convertView.findViewById(R.id.itme_feel_tvname);
            holder.mTvTime = (TextView) convertView.findViewById(R.id.itme_feel_tvtime);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.itme_feel_tvcontent);
            holder.mDraweeView0 = (SimpleDraweeView) convertView.findViewById(R.id.itme_feel_iv0);
            holder.mDraweeView1 = (SimpleDraweeView) convertView.findViewById(R.id.itme_feel_iv1);
            holder.mDraweeView2 = (SimpleDraweeView) convertView.findViewById(R.id.itme_feel_iv2);
            convertView.setTag(holder);
        }
        holder = (GViewHolder) convertView.getTag();
        holder.mDraweeView.setImageURI(Uri.parse("http://img2.3lian.com/2014/f2/37/d/33.jpg"));
        holder.mDraweeView0.setImageURI(Uri.parse("http://img2.3lian.com/2014/f2/37/d/33.jpg"));
        holder.mDraweeView1.setImageURI(Uri.parse("http://img2.3lian.com/2014/f2/37/d/33.jpg"));
        holder.mDraweeView2.setImageURI(Uri.parse("http://img2.3lian.com/2014/f2/37/d/33.jpg"));
        holder.mTvName.setText("为情霍去病");
        holder.mTvTime.setText("2017-01-02");
        holder.mTvContent.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容" +
                "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
        return convertView;
    }

    private class GViewHolder {
        SimpleDraweeView mDraweeView, mDraweeView0, mDraweeView1, mDraweeView2;
        TextView mTvName, mTvTime, mTvContent;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }


    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (childPosition == (getChildrenCount(groupPosition)) - 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        CViewHolder cViewHolder;
        CViewHolder_edit cViewHolder_edit;
        List<String> list = getDatas().get(groupPosition).getList();
        if (getChildType(groupPosition, childPosition) == 0) {
            if (convertView == null) {
                cViewHolder = new CViewHolder();
                convertView = getInflater().inflate(R.layout.itme_feel_c, parent, false);
                cViewHolder.mTextView = (TextView) convertView.findViewById(R.id.itme_feel_c_tvcontent);
                convertView.setTag(cViewHolder);
            }
            cViewHolder = (CViewHolder) convertView.getTag();
            cViewHolder.mTextView.setText(list.get(childPosition) + "");
        } else {
            if (convertView == null) {
                cViewHolder_edit = new CViewHolder_edit();
                convertView = getInflater().inflate(R.layout.itme_feel_c_edit, parent, false);
                cViewHolder_edit.mEditText = (TextView) convertView.findViewById(R.id.itme_feel_c_etpinglun);
                convertView.setTag(cViewHolder_edit);
            }
            cViewHolder_edit = (CViewHolder_edit) convertView.getTag();
            cViewHolder_edit.mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditingDialog(groupPosition, childPosition);
                }
            });
        }
        return convertView;
    }

    /**
     * 显示编辑评论内容 对话框
     */
    private void showEditingDialog(int groupPosition, int childPosition) {
        new DialogEtidingFeel(getmContext(), groupPosition, childPosition, new DialogEtidingFeel.CallBack() {
            @Override
            public void addFeel(String strings, int grounpPosition, int childPosition) {
                getDatas().get(grounpPosition).getList().add(strings);
                notifyDataSetChanged();
            }
        }).show();
    }

    private class CViewHolder {
        TextView mTextView;
    }

    private class CViewHolder_edit {
        TextView mEditText;
    }
}
