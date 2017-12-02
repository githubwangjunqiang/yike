package com.angel.interfaces;

/**
 * Created by ${王俊强} on 2017/4/16.
 */

public interface DiffUtilesListener {
    boolean contentsTheSame(int oldItemPosition,int newItemPosition);
    boolean areItemsTheSame(int oldItemPosition,int newItemPosition);
}
