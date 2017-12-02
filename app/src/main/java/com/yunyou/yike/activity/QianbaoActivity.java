package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerQianBaoCompcoet;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Money;
import com.yunyou.yike.presenter.QianBaoPresenter;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.util.Map;

import javax.inject.Inject;

public class QianbaoActivity extends BaseMVPActivity<IView.IQianBaoActivityView,
        QianBaoPresenter> implements IView.IQianBaoActivityView {
    private TextView mTextViewTitle;//标题
    private ImageView mImageViewBack;//返回按钮
    private TextView mTextViewMoney;
    private String money;
    @Inject
    QianBaoPresenter mQianBaoPresenter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_qianbao;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.qianbao_staslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.qianbao_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewMoney = optionView(R.id.tv_money);
        mTextViewTitle.setText(R.string.wodeqianbao);
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

    /**
     * 提现
     *
     * @param view
     */
    public void onClickWithdrawals(View view) {
        if (TextUtils.isEmpty(money)) {
            To.oo("您还没有余额");
            return;
        }
        String userToken = SpService.getSP().getUserToken(SpService.getSP().getPhone());
        Map<String, String> map = new ArrayMap<>();
        Map<String, String> mapNanmkList = new ArrayMap<>();
        map.put("money", money);
        map.put("token", userToken);
        mapNanmkList.put("token", userToken);
        mQianBaoPresenter.setWithdrawCash(mapNanmkList, map);
    }

    /**
     * 充值
     *
     * @param view
     */
    public void onClickRecharge(View view) {

    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mQianBaoPresenter.getMoney(map, isShowLoadingView);
    }


    @Override
    protected QianBaoPresenter mPresenterCreate() {
        DaggerQianBaoCompcoet.builder().presenterMobule(new PresenterMobule())
                .appCompcoent(((App) getApplication()).getAppCompcoent())
                .build().inject(this);
        return mQianBaoPresenter;
    }

    @Override
    public void showDataMoney(Money mon) {
        money = mon.getData();
        mTextViewMoney.setText(mon.getData());
    }

    @Override
    public void showWithdraw(String strings) {
        To.ss(mImageViewBack, strings);
    }
}
