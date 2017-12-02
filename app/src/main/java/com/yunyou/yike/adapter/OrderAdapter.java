package com.yunyou.yike.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.pullrefreshlayout.adapter.SWRecyclerAdapter;
import com.yan.pullrefreshlayout.adapter.SWViewHolder;
import com.yunyou.yike.R;
import com.yunyou.yike.activity.OrderInfoActivity;
import com.yunyou.yike.entity.Order;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class OrderAdapter extends SWRecyclerAdapter<Order.DataBean> {
    public interface Callback {
        void onClickStart(Order.DataBean dataBean, int position);

        void onClickCanle(Order.DataBean dataBean, int position);
    }

    private Callback mCallback;

    private boolean myOrderNumber;

    public void setMyOrderNumber(boolean myOrderNumber) {
        this.myOrderNumber = myOrderNumber;
    }

    public OrderAdapter(Context context, List<Order.DataBean> list, Callback callback) {
        super(context, list);
        mCallback = callback;
    }

    @Override
    protected void setAutoUtilsView(View view) {
        AutoUtils.auto(view);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.itme_order;
    }

    @Override
    public void bindData(SWViewHolder holder, final int position, final Order.DataBean item) {

        try {


            holder.setText(R.id.tvitme_fadan, "发单");
            holder.setText(R.id.itme_order_tvorder_sn, "订单编号：" + item.getOrder_sn());
            holder.setText(R.id.itme_order_tvorder_name, "" + item.getUser_name());//接单人姓名

//            List<String> word_type = item.getWord_type();
//            if (word_type != null) {
//                StringBuffer buffer = new StringBuffer();
//                for (int i = 0; i < word_type.size(); i++) {
//                    buffer.append(word_type.get(i));
//                }
//            }
            String info = "费用：" + item.getMoney() +
                    "\n工种：" + (item.getWord_type() == null ? "?" : item.getWord_type().toString()) +
                    "\n工作地址：" + item.getAddress();
            holder.setText(R.id.itme_order_tvorder_content, info);//详情
            String stats = "未开始";//0 未开始 1 进行中 2 已完成 3 取消订单
            if (item.getStatus() != null) {

                switch (item.getStatus()) {
                    case "0":
                        stats = "未开始";
                        break;
                    case "1":
                        stats = "进行中";
                        break;
                    case "2":
                        stats = "已完成";
                        break;
                    case "3":
                        stats = "已取消订单";
                        break;
                }
            }
            holder.setText(R.id.itme_order_tvorder_stas, stats);//进度状态
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.itme_order_sv_image);
            simpleDraweeView.setImageURI(Uri.parse(item.getHead_pic() + ""));
            if (item.getTime() != null) {
                long tissm = Long.parseLong(item.getTime()) * 1000L;
                String time = DateUtils.formatDateTime(getContext(), tissm, DateUtils.FORMAT_SHOW_DATE);
                holder.setText(R.id.itme_order_tvorder_time, "" + time);//抢单时间
            }
            holder.getView(R.id.itme_order_lltop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击进入详情页
                    OrderInfoActivity.startOrderInfoActivity(getContext(), item);
                }
            });
            Button mBtnStart = (Button) holder.getView(R.id.itme_order_btnorder_start);
            mBtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onClickStart(item, position);
                    }
                }
            });
            Button mBtnStop = (Button) holder.getView(R.id.itme_order_btnorder_stop);
            mBtnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onClickCanle(item, position);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
