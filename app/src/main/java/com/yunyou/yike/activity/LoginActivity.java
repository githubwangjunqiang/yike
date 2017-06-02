package com.yunyou.yike.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerLoginCompcope;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Login;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.presenter.LoginActivityPresenter;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;


public class LoginActivity extends BaseMVPActivity<IView.ILoginActivityView,
        LoginActivityPresenter> implements IView.ILoginActivityView {
    public static final int REGISTER = 1001;
    private EditText mEditTextPhone, mEditTextPas;
    @Inject
    LoginActivityPresenter mPresenter;
    private boolean isTokenLogin = false;
    public static final String TOKENLOGIN = "TOKENLOGIN";

    public static void startLoginActivity(Context context, boolean isTokenLogin) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(TOKENLOGIN, isTokenLogin);
        context.startActivity(intent);
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.login_staslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void beforeWindow(Bundle savedInstanceState) {
        super.beforeWindow(savedInstanceState);
        statusBarColor = Color.TRANSPARENT;
        getWindow().setBackgroundDrawable(null);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mEditTextPhone = optionView(R.id.login_etphone);
        mEditTextPas = optionView(R.id.login_etmima);
        transparent19and20();
        hideStatusBar();
        isTokenLogin = getIntent().getBooleanExtra(TOKENLOGIN, false);
        if (!isTokenLogin) {
            startRefresh(null);
        }
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {
        showContentView(null);
        if (message.getMsgCode() == EventBusMessage.LOCATION) {
            String phone = mEditTextPhone.getText().toString().trim();
            if (!TextUtils.isEmpty(phone) && !isTokenLogin) {
                String userToken = SpService.getSP().getUserToken(phone);
                if (!TextUtils.isEmpty(userToken)) {
                    showLoodingDialog(null);
                    toMainActivity();
                }
            }
        }

    }


    @Override
    public void startRefresh(Object object) {
        String phone = SpService.getSP().getPhone();
        String pass = SpService.getSP().getPas();
        if (!TextUtils.isEmpty(phone)) {
            mEditTextPhone.setText(phone);
        }
        if (!TextUtils.isEmpty(pass)) {
            mEditTextPas.setText(pass);
        }
        String[] persion = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,//网络位置
                Manifest.permission.ACCESS_FINE_LOCATION,//访问精细位置
                Manifest.permission.READ_PHONE_STATE,//手机信息
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入存储卡
                Manifest.permission.READ_EXTERNAL_STORAGE,//读取存储卡
        };
        PermissionActivity.startPermissionActivity(persion, new PermissionListener() {
            @Override
            public void success() {
                showDialog("正在定位您当前的位置");
                if (!App.getLocationClient().isStarted()) {
                    App.getLocationClient().start();
                }

            }

            @Override
            public void error(String[] permission) {
                To.ee("您关闭了重要权限，影响您的使用，请您手动去权限管理中心开启本应用权限亲");
            }
        });
    }


    @Override
    protected LoginActivityPresenter mPresenterCreate() {
        DaggerLoginCompcope.builder()
                .presenterMobule(new PresenterMobule())
                .appCompcoent(((App) getApplication()).getAppCompcoent())
                .build()
                .inject(this);
        return mPresenter;
    }

    /**
     * 点击登陆
     *
     * @param view
     */
    public void logIn(View view) {
        if (App.getBDLocation() == null) {
            To.ee("请稍后，尚未定位成功");
            if (!App.getLocationClient().isStarted()) {
                startRefresh(null);
            }
            return;
        }
        String phone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            To.oo("账号不能为空");
            return;
        }
        String pass = mEditTextPas.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            To.oo("密码不能为空");
            return;
        }
        mPresenter.login(phone, pass,
                JPushInterface.getRegistrationID(App.getContext()),
                App.getBDLocation().getLongitude() + "", App.getBDLocation().getLatitude() + "");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isTokenLogin) {
            App.appOut();
            System.exit(0);
        }
    }

    @Override
    protected void hideLoadingListener() {
    }

    @Override
    public void loginSuccess(Login login) {//登陆成功
        Login.DataBean data = login.getData();
        if (data == null) {
            To.ee("数据返回缺失");
            return;
        }
        App.setUserId(login.getData().getUser_id());
        SpService.getSP().saveToken(login.getData().getToken(), login.getData().getUser_id()
                , mEditTextPhone.getText().toString().trim());
        if (!isTokenLogin) {
            toMainActivity();
        }
    }

    /**
     * 关闭登陆页面 进入主页
     */
    private void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        SpService.getSP().savePhone(mEditTextPhone.getText().toString().trim(),
                mEditTextPas.getText().toString().trim());
        super.onDestroy();
    }
}
