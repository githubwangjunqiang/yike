package com.yunyou.yike.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerOrderInfoCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Order;
import com.yunyou.yike.presenter.OrderInfoPresenter;
import com.yunyou.yike.utils.SpService;

import java.util.Map;

import javax.inject.Inject;

public class OrderInfoActivity extends BaseMVPActivity<IView.IOrderInfoActivityView, OrderInfoPresenter>
        implements IView.IOrderInfoActivityView {
    private static final String DATABEAN = "databean";
    private TextView mTextViewTitle, mTextViewStats, mTextViewUserName, mTextViewPrice,
            mTextViewContnent, mTextViewNumber;
    private ImageView mImageViewBack;
    private SimpleDraweeView mDraweeView;
    private RatingBar mRatingBar;
    @Inject
    OrderInfoPresenter mOrderInfoPresenter;

    public static void startOrderInfoActivity(Context context, Order.DataBean dataBean) {
        Intent intent = new Intent(context, OrderInfoActivity.class);
        intent.putExtra(DATABEAN, dataBean);
        context.startActivity(intent);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_info;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.job_looking_statslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.job_looking_pullayout;
    }

    @Override
    protected boolean beforeWindow(Bundle savedInstanceState) {
        if (getIntent().getParcelableExtra(DATABEAN) == null) {
            return true;
        }
        return super.beforeWindow(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDraweeView = optionView(R.id.order_info_iamgeview);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewStats = optionView(R.id.tvinfo_fuwu);
        mTextViewUserName = optionView(R.id.orderinfo_tvname);
        mRatingBar = optionView(R.id.orderinfo_ratbar);
        mTextViewPrice = optionView(R.id.tvprice_info);
        mTextViewContnent = optionView(R.id.orderinfo_tvcontent);
        mTextViewNumber = optionView(R.id.orderinfo_tvnumner);
        mTextViewTitle.setText(R.string.dingdanxiangqing);

        startRefresh(true);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    protected OrderInfoPresenter mPresenterCreate() {
        DaggerOrderInfoCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule())
                .build().inject(this);
        return mOrderInfoPresenter;
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        if (isShowLoadingView) {
            showLoodingView(null);
        }
        Order.DataBean dataBean = getIntent().getParcelableExtra(DATABEAN);
        String head_pic = dataBean.getHead_pic();
        if (!TextUtils.isEmpty(head_pic)) {
            mDraweeView.setImageURI(Uri.parse(head_pic));
        }
        String status = dataBean.getStatus();
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    status = "未开始";
                    break;
                case "1":
                    status = "进行中";
                    break;
                case "2":
                    status = "已完成";
                    break;
                case "3":
                    status = "已取消订单";
                    break;
                default:
                    status = "未开始";
                    break;
            }
            mTextViewStats.setText(status);
        }
        String user_name = dataBean.getUser_name();
        if (!TextUtils.isEmpty(user_name)) {
            mTextViewUserName.setText(user_name);
        }
        //评分
        mRatingBar.setRating(3F);

        String money = dataBean.getMoney();
        if (!TextUtils.isEmpty(money)) {
            mTextViewPrice.setText(money + "/总价");
        }
        //内容
        mTextViewContnent.setText("具体内容具体内容具体内容具体内容具体内容具体内容具体内容具体内容" +
                "具体内容具体内容具体内容具体内容具体内容具体内容具体内容具体内容");
        //接单量
        mTextViewNumber.setText("近期接单量：000\\n特长：特长名称");
        showContentView(null);
    }

    /**
     * 点击终止订单
     *
     * @param view
     */
    public void canleOrder(View view) {
        Order.DataBean dataBean = getIntent().getParcelableExtra(DATABEAN);
        Map<String, String> map = new ArrayMap<>();
        map.put("id", dataBean.getId());
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mOrderInfoPresenter.canleOrder(map);
    }

    /**
     * 点击完成订单
     *
     * @param view
     */
    public void finshOrder(View view) {
        Order.DataBean dataBean = getIntent().getParcelableExtra(DATABEAN);
        Map<String, String> map = new ArrayMap<>();
        map.put("id", dataBean.getId());
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mOrderInfoPresenter.finishOrder(map);
    }
}
