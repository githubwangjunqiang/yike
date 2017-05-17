package com.yunyou.yike.ExListViewAdapter;

import android.widget.AbsListView;

/**
 * Created by ${王俊强} on 2017/4/17.
 */

public abstract class ListenerScrollListener implements AbsListView.OnScrollListener {


    private boolean isLoading = false;//是否加载完成
    private boolean isLastPosition = false;//是否最后一个position 出现
    private int count;//记录itme的总数


    public abstract void loadMore();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                // 飞快滑动的状态
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 空闲时的状态
                // 此时如果滑到最底部 加载数据
                if (isLoading && isLastPosition) {
                    isLoading = false;
                    loadMore();
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // 手指拖动的状态
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int f, int v, int t) {
        if (count != t) {
            count = t;
            isLoading = true;
        }

        if (f + v == t) {
            isLastPosition = true;
        } else {
            isLastPosition = false;
        }

    }
}
