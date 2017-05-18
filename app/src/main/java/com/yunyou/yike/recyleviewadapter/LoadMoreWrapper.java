package com.yunyou.yike.recyleviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by ${王俊强} on 2017/4/17.
 */

public abstract class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_FAILED_VIEW = Integer.MAX_VALUE - 1;//加载失败
    public static final int ITEM_TYPE_NO_MORE_VIEW = Integer.MAX_VALUE - 2;//停止加载
    public static final int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 3;//加载中

    private Context mContext;
    private boolean isLoadError = false;//是否加载失败

    public Context getmContext() {
        return mContext;
    }

    private LayoutInflater inflater;

    public LayoutInflater getInflater() {
        return inflater;
    }

    private View mLoadMoreView;
    private View mLoadMoreFailedView;
    private View mNoMoreView;

    private LoadMoreScrollListener mLoadMoreScrollListener;

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(@NonNull List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        addLastItmeType(ITEM_TYPE_LOAD_MORE_VIEW);
    }

    public LoadMoreWrapper(@NonNull Context mContext, @NonNull List<T> list) {
        this.mContext = mContext;
        this.list = list;
        addLastItmeType(ITEM_TYPE_LOAD_MORE_VIEW);
        this.inflater = LayoutInflater.from(mContext);
        mLoadMoreScrollListener = new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                if (mOnLoadListener != null) {
                    if (!isLoadError) {
                        showLoadMore();
                        mOnLoadListener.onLoadMore();
                    }
                }
            }
        };


    }

    /**
     * 显示加载更多状态中
     */
    private void showLoadMore() {
        unLastItmeType(ITEM_TYPE_LOAD_MORE_VIEW);
        notifyItemChanged(getItemCount());
    }

    /**
     * 修改最后一项的类型
     *
     * @param itmeType 要修改的类型
     */
    protected abstract void unLastItmeType(int itmeType);

    /**
     * 在最后一项添加制定的类型
     *
     * @param itmeType 要修改的类型
     */
    protected abstract void addLastItmeType(int itmeType);

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }


    protected abstract int getItemType(int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NO_MORE_VIEW) {//加载完成，停止加载
            return getNoMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_MORE_VIEW) {//加载中
            return getLoadMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {//加载失败
            return getLoadFailedViewHolder();
        }
        return onCreateHolder(parent, viewType);
    }

    protected abstract ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    /**
     * Gets the state of the failed load holder
     *
     * @return
     */
    private ViewHolder getLoadFailedViewHolder() {
        if (mLoadMoreFailedView == null) {
            mLoadMoreFailedView = new TextView(mContext);
            mLoadMoreFailedView.setPadding(20, 20, 20, 20);
            mLoadMoreFailedView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ((TextView) mLoadMoreFailedView).setText("加载失败，请点我重试");
            ((TextView) mLoadMoreFailedView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mLoadMoreFailedView);
    }

    /**
     * Gets the state of holder in the load
     *
     * @return
     */
    private ViewHolder getLoadMoreViewHolder() {
        if (mLoadMoreView == null) {
            mLoadMoreView = new TextView(mContext);
            mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            mLoadMoreView.setPadding(20, 20, 20, 20);
            ((TextView) mLoadMoreView).setText("正在加载中");
            ((TextView) mLoadMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mLoadMoreView);
    }

    /**
     * Gets the state of stop loading holder
     *
     * @return
     */
    private ViewHolder getNoMoreViewHolder() {
        if (mNoMoreView == null) {
            mNoMoreView = new TextView(mContext);
            mNoMoreView.setPadding(20, 20, 20, 20);
            mNoMoreView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ((TextView) mNoMoreView).setText("--end--");
            ((TextView) mNoMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.createViewHolder(mContext, mNoMoreView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_LOAD_FAILED_VIEW) {//Load failed state
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadListener != null) {
                        mOnLoadListener.onRetry();
                        showLoadMore();
                    }
                }
            });
            return;
        }
        if (!isFooterType(holder.getItemViewType())) {

            onBindHolder((ViewHolder) holder, position);
        }
    }

    protected abstract void onBindHolder(ViewHolder holder, int position);

    /**
     * Whether it is the bottom of the four state values
     *
     * @param type
     * @return
     */
    public boolean isFooterType(int type) {

        return type == ITEM_TYPE_LOAD_FAILED_VIEW ||
                type == ITEM_TYPE_NO_MORE_VIEW ||
                type == ITEM_TYPE_LOAD_MORE_VIEW;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //region 加载监听

    public interface OnLoadListener {
        void onRetry();

        void onLoadMore();
    }

    private OnLoadListener mOnLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    /**
     * 显示失败的状态
     */
    public void showLoadError() {
        isLoadError = true;
        unLastItmeType(ITEM_TYPE_LOAD_FAILED_VIEW);
        notifyItemChanged(getItemCount());
    }

    /**
     * 显示加载完成，添加数据
     */
    public void showLoadSuccess(List<T> listNews) {
        for (int i = 0; i < list.size(); i++) {
            if (i != (list.size() - 1)) {
                listNews.add(0, list.get(i));
            }
        }
        addRefresh(list, listNews, ITEM_TYPE_LOAD_MORE_VIEW);
    }

    /**
     * 刷新整体数据
     */
    public void reFreshSuccess(List<T> listNews) {
        addRefresh(list, listNews, ITEM_TYPE_LOAD_MORE_VIEW);
    }


    /**
     * 比较完后 要在新数据里面添加最后一个类型
     *
     * @param listOlds
     * @param listNews
     * @param itemTypeLoadMoreView 制定的类型
     */
    protected abstract void addRefresh(List<T> listOlds, List<T> listNews, int itemTypeLoadMoreView);


    /**
     * 显示加载到底部了
     */
    public void disableLoadMore() {
        unLastItmeType(ITEM_TYPE_NO_MORE_VIEW);
        notifyItemChanged(getItemCount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (position == (getItemCount() - 1)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
        recyclerView.addOnScrollListener(mLoadMoreScrollListener);
    }


    //
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

        if (holder.getLayoutPosition() == getItemCount() - 1) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }

}
