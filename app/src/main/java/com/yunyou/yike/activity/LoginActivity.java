package com.yunyou.yike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fingdo.statelayout.StateLayout;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerDaggerCompcoent;
import com.yunyou.yike.presenter.LoginActivityPresenter;

import javax.inject.Inject;

public class LoginActivity extends BaseMVPActivity<IView.ILoginActivityView,
        LoginActivityPresenter> implements IView.ILoginActivityView {
    private StateLayout mStateLayout;
    private LinearLayout mLinearLayout;
    public static final int REGISTER = 1001;
    private EditText mEditTextPhone, mEditTextPas;
    @Inject
    LoginActivityPresenter mPresenter;

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mStateLayout = optionView(R.id.login_staslayout);
        mStateLayout.setUseAnimation(true);
        mLinearLayout = optionView(R.id.ll_content);
        mEditTextPhone = optionView(R.id.login_etphone);
        mEditTextPas = optionView(R.id.login_etmima);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void startRefresh(Object object) {

    }


    @Override
    protected LoginActivityPresenter mPresenterCreate() {
        DaggerDaggerCompcoent.create().inject(this);
        return mPresenter;
    }

    /**
     * 点击登陆
     *
     * @param view
     */
    public void logIn(View view) {
        mStateLayout.showLoadingView();
        mStateLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateLayout.showContentView();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);

    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        String phone = mEditTextPhone.getText().toString().trim();
        startActivityForResult(new Intent(this, RegisterActivity.class).putExtra(RegisterActivity.REGISTENER_PHONE, phone), REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER && resultCode == RESULT_OK) {//注册成功返回
            String phone = data.getStringExtra(RegisterActivity.REGISTENER_PHONE);
            String pass = data.getStringExtra(RegisterActivity.REGISTER_PASS);
            if (!TextUtils.isEmpty(phone)) {
                mEditTextPhone.setText(phone);
            }
            if (!TextUtils.isEmpty(pass)) {
                mEditTextPas.setText(pass);
            }
        }
    }
}
