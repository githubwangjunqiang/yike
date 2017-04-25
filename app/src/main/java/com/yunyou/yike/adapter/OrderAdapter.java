package com.yunyou.yike.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/25.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<Order> mList;
    private Context mContext;

    public OrderAdapter(List<Order> list, Context context) {
        mList = list == null ? new ArrayList<>() : list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    public void setList(List<Order> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void loodList(List<Order> list) {
        this.mList.remove(mList.size() - 1);
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void loodError() {
        this.mList.get(mList.size() - 1).setName("已经到底了");
        notifyItemChanged(getItemCount());
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new OrderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.itme_order,
                        parent, false), viewType);
            case Integer.MAX_VALUE - 1:
                return new OrderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.itme_order_tvjiazai,
                        parent, false), viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder {
    private TextView mTvName, mTvOrderSn, mTvOrderTime, mTvOrderContent, mTvOrderStas;
    private Button mBtnStart, mBtnStop;
    private SimpleDraweeView mDraweeView;
    private TextView mTvJiaZai;

    public OrderViewHolder(View itemView, int type) {
        super(itemView);
        if (type == 0) {
            mTvName = (TextView) itemView.findViewById(R.id.itme_order_tvorder_name);
            mTvOrderSn = (TextView) itemView.findViewById(R.id.itme_order_tvorder_sn);
            mTvOrderTime = (TextView) itemView.findViewById(R.id.itme_order_tvorder_time);
            mTvOrderContent = (TextView) itemView.findViewById(R.id.itme_order_tvorder_content);
            mTvOrderStas = (TextView) itemView.findViewById(R.id.itme_order_tvorder_stas);
            mBtnStart = (Button) itemView.findViewById(R.id.itme_order_btnorder_start);
            mBtnStop = (Button) itemView.findViewById(R.id.itme_order_btnorder_stop);
            mDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.itme_order_sv_image);
        } else if (type == (Integer.MAX_VALUE - 1)) {
            mTvJiaZai = (TextView) itemView.findViewById(R.id.itme_order_tvjiazai);
        }
    }

    public void setData(Order data) {
        if (data.getType() == 0) {
            try {
                mTvOrderSn.setText("订单编号：00000000");
                mTvName.setText("林老板");
                mTvOrderTime.setText("2017/0/0\t00:00");
                mTvOrderContent.setText("费用：300\n工种：工种\n工作地址：王府井大街，北京饭店");
                mTvOrderStas.setText("未服务");
                mDraweeView.setImageURI(Uri.parse("http://ww3.sinaimg.cn/mw600/9e3e41d9jw1dt87pn9kk9j.jpg"));
                mBtnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        To.oo("开始");
                    }
                });
                mBtnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        To.oo("结束");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(e.getMessage());
            }

        } else if (data.getType() == Integer.MAX_VALUE - 1) {
            mTvJiaZai.setText(data.getName());
        }
    }


}
