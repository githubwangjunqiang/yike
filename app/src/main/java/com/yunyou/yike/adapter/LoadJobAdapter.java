package com.yunyou.yike.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.recyleviewadapter.DiffUtilesListener;
import com.yunyou.yike.recyleviewadapter.LoadMoreWrapper;
import com.yunyou.yike.recyleviewadapter.SWDiffCallBack;
import com.yunyou.yike.recyleviewadapter.ViewHolder;
import com.yunyou.yike.utils.To;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/17.
 */

public class LoadJobAdapter extends LoadMoreWrapper<JobList> {
    public LoadJobAdapter(@NonNull Context mContext, @NonNull List<JobList> list) {
        super(mContext, list);
    }

    @Override
    protected void unLastItmeType(int itmeType) {
        getList().get(getList().size() - 1).setType(itmeType);
    }

    @Override
    protected void addLastItmeType(int itmeType) {
        getList().add(new JobList(null, itmeType));
    }

    @Override
    protected int getItemType(int position) {
        return getList().get(position).getType();
    }

    @Override
    protected ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(getmContext(), parent, R.layout.itme_job_recylerview);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, int position) {
        SimpleDraweeView imageView = holder.getView(R.id.itme_job_image);
        imageView.setImageURI(Uri.parse("http://p0.so.qhmsg.com/bdr/_240_/t011b50c179939efc75.jpg"));
        holder.setText(R.id.itme_job_tvname, "艺科小姐");
        RatingBar ratingBar = holder.getView(R.id.itme_job_ratingbar);
        ratingBar.setRating(position % 5 + 1);
        holder.setText(R.id.itme_job_tvcontent, "工种：工种\t\t姓名：姓名\n工作地址：王府井大街\t北京饭店\n合计价格：300.00");
        holder.setText(R.id.itme_job_tvtime, "2000/01/02\t08:08");
        holder.setOnClickListener(R.id.itme_job_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                To.oo("抢单");
            }
        });
    }

    @Override
    protected void addRefresh(List<JobList> listOldss, List<JobList> listNews, int itemTypeLoadMoreView) {
        DiffUtil.calculateDiff(new SWDiffCallBack<JobList>(listOldss, listNews, new DiffUtilesListener<JobList>() {
            @Override
            public boolean contentsTheSame(List<JobList> olddatas, int oldItemPosition, List<JobList> newDatas, int newItemPosition) {
                return olddatas.get(oldItemPosition).getType()
                        == newDatas.get(newItemPosition).getType();
            }

            @Override
            public boolean areItemsTheSame(List<JobList> olddatas, int oldItemPosition, List<JobList> newDatas, int newItemPosition) {
                return olddatas.get(oldItemPosition).getType()
                        == newDatas.get(newItemPosition).getType() ||
                        olddatas.get(oldItemPosition).getName()
                                .equals(newDatas.get(newItemPosition).getType());
            }
        })).dispatchUpdatesTo(this);

        listOldss = listNews;
        listOldss.add(new JobList(null, itemTypeLoadMoreView));

    }
}
