package com.yunyou.yike.recyleviewadapter;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/4/16.
 */

public interface DiffUtilesListener<T> {
    boolean contentsTheSame(List<T> olddatas, int oldItemPosition, List<T> newDatas, int newItemPosition);
    boolean areItemsTheSame(List<T> olddatas, int oldItemPosition, List<T> newDatas, int newItemPosition);
}
