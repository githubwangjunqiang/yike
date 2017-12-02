package com.yunyou.yike.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;

import com.angel.adapter.SWRecyclerAdapter;
import com.angel.adapter.SWViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.JobList;
import com.yunyou.yike.utils.DateUtil;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/17.
 */

public class LoadJobAdapter extends SWRecyclerAdapter<JobList.DataBean> {

    public LoadJobAdapter(Context context, List<JobList.DataBean> list) {
        super(context, list);
    }

    @Override
    protected void setAutoUtilsView(View view) {
        AutoUtils.auto(view);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.itme_job_recylerview;
    }

    @Override
    public void bindData(SWViewHolder holder, int position, final JobList.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        SimpleDraweeView imageView = (SimpleDraweeView) holder.getView(R.id.itme_job_image);
        String head_pic = dataBean.getHead_pic();

        if (!TextUtils.isEmpty(head_pic)) {
            imageView.setImageURI(Uri.parse(head_pic));
        }
        String nickname = dataBean.getNickname();
        if (!TextUtils.isEmpty(nickname)) {
            holder.setText(R.id.itme_job_tvname, nickname);
        }
        String rank = dataBean.getRank();
        if (!TextUtils.isEmpty(rank)) {
            RatingBar ratingBar = (RatingBar) holder.getView(R.id.itme_job_ratingbar);
            ratingBar.setRating(Float.parseFloat(rank));
        }


        holder.setText(R.id.itme_job_tvcontent, "工种：" + dataBean.getWork_type() + "\t\t姓名：" +
                dataBean.getUser_name() + "\n" +
                "工作地址：" + dataBean.getAddress() + "\n合计价格：" + dataBean.getMoney());

        if (!TextUtils.isEmpty(dataBean.getAdd_time())) {
            String friendlyTime = DateUtil.getFriendlyTime(new Date(Long.parseLong(dataBean.getAdd_time()) * 1000));
            holder.setText(R.id.itme_job_tvtime, friendlyTime + "");
        }
        holder.getView(R.id.itme_job_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusMessage(EventBusMessage.CONFIRMORDER,
                        dataBean));
            }
        });
    }

}
