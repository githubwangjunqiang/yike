package com.yunyou.yike.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.utils.ActivityCollector;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends BaseActivity {
    private static PermissionListener mPermissionListener;
    private static final int permissionsCode = 123;
    private String[] mPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissions = (String[]) getIntent().getSerializableExtra("permissions");
        if (mPermissions == null || mPermissions.length < 1) {
            sendSucess();
            return;
        }
        initPermissions();
    }



    /**
     * 请求 授权
     */
    private void initPermissions() {
        try {
            List<String> list = new ArrayList<>();
            for (String permission : mPermissions) {
                if (ContextCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {//等于授权
                    list.add(permission);
                }
            }
            if (list.isEmpty()) {//如果是空的 说明 已经授权 返回成功
                sendSucess();
            } else {
                ActivityCompat.requestPermissions(this,
                        list.toArray(new String[list.size()]), permissionsCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendEeeoe(mPermissions);
        }

    }

    /**
     * 发送成功 回掉
     */
    private void sendSucess() {
        mPermissionListener.success();
        finish();
        overridePendingTransition(0, 0);

    }

    /**
     * 失败
     */
    private void sendEeeoe(String[] permissions) {
        mPermissionListener.error(permissions);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * 启动权限界面
     */
    public static void startPermissionActivity(String[] permissions, PermissionListener mlistener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            To.oo("上下文 请求失败");
            mlistener.error(permissions);
            return;
        }
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {//等于授权
                list.add(permission);
            }
        }
        if (list.isEmpty()) {//如果是空的 说明 已经授权 返回成功
            mlistener.success();
        } else {
            Intent intent = new Intent(topActivity, PermissionActivity.class);
            intent.putExtra("permissions", permissions);
            topActivity.startActivity(intent);
            topActivity.overridePendingTransition(0, 0);
            mPermissionListener = mlistener;
        }
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionsCode && grantResults.length > 0) {//判断
            List<String> mlist = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    mlist.add(permissions[i]);
                }
            }
            if (mlist.isEmpty()) {
                sendSucess();
            } else {
                sendEeeoe(mlist.toArray(new String[]{}));
            }
        } else {
            sendEeeoe(mPermissions);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return 0;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void startRefresh(Object object) {

    }
}
