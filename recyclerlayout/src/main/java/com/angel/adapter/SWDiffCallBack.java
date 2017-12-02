package com.angel.adapter;

import android.support.v7.util.DiffUtil;

import com.angel.interfaces.DiffUtilesListener;

import java.util.List;

/**
 * Created by Angel on 2017/4/10.
 */
public class SWDiffCallBack<T> extends DiffUtil.Callback {

    private List<T> olddatas;
    private List<T> newDatas;
    private DiffUtilesListener listener;

    public SWDiffCallBack(List<T> olddatas, List<T> newDatas, DiffUtilesListener listener) {
        if (null == olddatas || null == newDatas || listener == null) {
            throw new NullPointerException("SWDiffCallBack 构造方法不能有null值");
        }
        this.olddatas = olddatas;
        this.newDatas = newDatas;
        this.listener = listener;
    }

    public int getOldListSize() {
        return olddatas.size();
    }

    public int getNewListSize() {
        return newDatas.size();
    }

    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return listener.areItemsTheSame(oldItemPosition, newItemPosition);
    }

    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return listener.contentsTheSame(oldItemPosition, newItemPosition);
    }


}
