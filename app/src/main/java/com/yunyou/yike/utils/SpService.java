package com.yunyou.yike.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yunyou.yike.AppManager;


public class SpService {
    private Context context;
    private static SpService sp;
    private final String appName = AppManager.getInstance().getContext().getPackageName();
    private final String PHONE = "phone";
    private final String PASSSWORD = "pass";
    private final String RTOKEN = "r_token";
    private final String RONGIMUSERNAM = "r_userId";
    private final String TOKEN = "user_token";
    private final String USERID = "user_id";

    private SpService(Context context) {
        this.context = context;
    }

    public static SpService getSP() {
        if (sp == null) {
            synchronized (SpService.class) {
                if (sp == null) {
                    sp = new SpService(AppManager.getInstance().getContext());
                }
            }
        }
        return sp;
    }


    /**
     * 只存入电话 密码
     *
     * @param phone
     * @param pas
     * @return
     */
    public boolean savePhone(String phone, String pas) {
        SharedPreferences preferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(PHONE, phone);
        editor.putString(PASSSWORD, pas);
        return editor.commit();
    }

    /**
     * {"token":"7de8a7787ee12357142de898ea24b0a6",
     * "user_id":"10","rank":"0.0","head_pic":"","is_listen":"1"}
     * 存入 token
     */
    public boolean saveToken(String token, String user_id, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(TOKEN + phone, token);
        editor.putString(USERID + phone, user_id);
        return editor.commit();
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public String getUserToken(String phone) {
        return getStringName(TOKEN + phone, appName);
    }
    /**
     * 获取用户userid
     *
     * @return
     */
    public String getUserId(String phone) {
        return getStringName(USERID + phone, appName);
    }

    /**
     * 写入融云token
     *
     * @return
     */
    public boolean saveR_Token(String phone,String token) {
        SharedPreferences preferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(RTOKEN+phone, token);
        return editor.commit();
    }
    /**
     * 写入融云 userid
     *
     * @return
     */
    public boolean saveR_userId(String phone,String saveR_userId) {
        SharedPreferences preferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(RONGIMUSERNAM+phone, saveR_userId);
        return editor.commit();
    }

    /**
     * 获取融云token
     */
    public String getR_Token(String phone) {
        return getStringName(RTOKEN+phone, appName);
    }

    /**
     * 获取融云 userid
     */
    public String getR_userid(String phone) {
        return getStringName(RONGIMUSERNAM+phone, appName);
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPas() {
        return getStringName(PASSSWORD, appName);
    }

    /**
     * 获取电话号码
     */
    public String getPhone() {
        return getStringName(PHONE, appName);
    }


    /**
     * 根据名称 获取值
     *
     * @param name
     * @param content
     * @return
     */
    private String getStringName(String name, String content) {
        SharedPreferences preferences = context.getSharedPreferences(content, Context.MODE_PRIVATE);
        String string = preferences.getString(name, "");
        return string;
    }

    /**
     * clear 存入的 user信息
     */
    public void clearUser() {
        SharedPreferences preferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        preferences.edit().clear();
    }

}
