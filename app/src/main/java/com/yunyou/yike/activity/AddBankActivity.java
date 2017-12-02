package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerAddBankCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.AddBankPresenter;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.util.Map;

import javax.inject.Inject;

public class AddBankActivity extends BaseMVPActivity<IView.IAddBankActivityView, AddBankPresenter>
        implements IView.IAddBankActivityView {
    @Inject
    AddBankPresenter mAddBankPresenter;
    private ProgressBar mProgressBar;
    private EditText mEditTextUserName, mEditTextBankNumber;
    private TextView mTextViewBankName;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mProgressBar = optionView(R.id.progressBar2);
        mEditTextUserName = optionView(R.id.addbank_edname);
        mEditTextBankNumber = optionView(R.id.addbank_ednumber);
        mTextViewBankName = optionView(R.id.addbank_tvbankname);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle.setText("添加银行卡");
    }

    @Override
    protected void setListener() {
        mEditTextBankNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextViewBankName.setText("银行名称:");
            }
        });
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
    protected AddBankPresenter mPresenterCreate() {
        DaggerAddBankCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mAddBankPresenter;
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }

    @Override
    public void showBankName(String bankName) {
        mProgressBar.setVisibility(View.GONE);
        mTextViewBankName.setText("银行名称：" + bankName);
    }

    @Override
    public void showBadBankSuccess(String string) {
        To.dd(string);
        setResult(RESULT_OK, getIntent());
        finish();
    }


    /**
     * 点击确认添加银行卡
     *
     * @param view
     */
    public void addBank(View view) {
        String account_bank = mEditTextBankNumber.getText().toString().trim();
        if (TextUtils.isEmpty(account_bank)) {
            To.ss(mEditTextBankNumber, "银行卡号码不能为空！");
            return;
        }
        String account_name = mEditTextUserName.getText().toString().trim();
        if (TextUtils.isEmpty(account_name)) {
            To.ss(mEditTextBankNumber, "用户名称不能为空！");
            return;
        }
        String bank_name = mTextViewBankName.getText().toString().trim();
        if (TextUtils.isEmpty(account_name) || bank_name.length() < 6) {
            To.ss(mEditTextBankNumber, "请点击获取银行名称按钮，获取银行名称");
            return;
        }
        bank_name = bank_name.substring(4, bank_name.length());
        Map<String, String> map = new ArrayMap<>();
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        map.put("account_bank", account_bank);
        map.put("account_name", account_name);
        map.put("bank_name", bank_name);
        mAddBankPresenter.bandBank(map);
    }

    /**
     * 点击获取银行卡名称
     *
     * @param view
     */
    public void getBankName(View view) {
        String trim = mEditTextBankNumber.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.oo("请输入银行卡号");
            return;
        }
        Map<String, String> map = new ArrayMap<>();
        map.put("account_bank", trim);
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mAddBankPresenter.getBankName(map);
        mProgressBar.setVisibility(View.VISIBLE);


    }
}
