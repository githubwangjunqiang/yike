package com.yunyou.yike.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yunyou.yike.App;
import com.yunyou.yike.AppManager;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerLoginCompcope;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Login;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.presenter.LoginActivityPresenter;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;

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
    protected boolean beforeWindow(Bundle savedInstanceState) {
        statusBarColor = Color.TRANSPARENT;
        getWindow().setBackgroundDrawable(null);
        return super.beforeWindow(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mEditTextPhone = optionView(R.id.login_etphone);
        mEditTextPas = optionView(R.id.login_etmima);
        transparent19and20();
        hideStatusBar();
        isTokenLogin = getIntent().getBooleanExtra(TOKENLOGIN, false);
        if (!isTokenLogin) {
            startRefresh(false);
        }
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {
        showContentView(null);
        if (message.getMsgCode() == EventBusMessage.LOCATION) {
            if (!isTokenLogin) {
                logIn(null);
            }
        }

    }


    @Override
    public void startRefresh(boolean isShowLoadingView) {
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
                AppManager.getInstance().startLocationClient();
                LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
                    To.oo("未打开位置开关，可能导致定位失败或定位不准");
                }

            }

            @Override
            public void error(String[] permission) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
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
        if (AppManager.getInstance().getBDLocation() == null) {
            To.ee("请稍后，尚未定位成功");
            if (!AppManager.getInstance().getLocationClient().isStarted()) {
                startRefresh(false);
            } else {
                AppManager.getInstance().getLocationClient().requestLocation();
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
                JPushInterface.getRegistrationID(AppManager.getInstance().getContext()),
                AppManager.getInstance().getBDLocation().getLongitude() + "",
                AppManager.getInstance().getBDLocation().getLatitude() + "");
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
        SpService.getSP().savePhone(mEditTextPhone.getText().toString().trim(),
                mEditTextPas.getText().toString().toString());
        SpService.getSP().saveR_Token(SpService.getSP().getPhone(), data.getR_token());
        SpService.getSP().saveToken(data.getToken(), data.getUser_id()
                , mEditTextPhone.getText().toString().trim());
        toMainActivity(isTokenLogin);
    }

    /**
     * 关闭登陆页面 进入主页
     */
    private void toMainActivity(final boolean isTokenLogins) {
        String trim = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return;
        }
        showLoodingDialog("正在登陆通讯系统...");

        LogUtils.d("登陆融云phone=" + trim);
        AppManager.getInstance().loginRongIM(SpService.getSP().getR_Token(trim));
        if (!isTokenLogins) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            EventBus.getDefault().post(new EventBusMessage(EventBusMessage.TOKENLOGIN));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        SpService.getSP().savePhone(mEditTextPhone.getText().toString().trim(),
                mEditTextPas.getText().toString().trim());
        super.onDestroy();
    }
}
