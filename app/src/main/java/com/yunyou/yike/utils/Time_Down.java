package com.yunyou.yike.utils;

import android.os.CountDownTimer;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by ${王俊强} on 2017/5/23.
 */

public class Time_Down {
    /**
     * 在按钮上启动一个定时器
     *
     * @param tvVerifyCode  验证码控件
     * @param defaultString 按钮上默认的字符串
     * @param max           失效时间（单位：s）
     * @param interval      更新间隔（单位：s）
     */
    public static void startTimer(
            final WeakReference<Button> tvVerifyCode,
            final String defaultString,
            int max,
            int interval) {
        tvVerifyCode.get().setEnabled(false);

        new CountDownTimer(max * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long time) {
                if (null == tvVerifyCode.get()) {
                    this.cancel();
                } else {
                    tvVerifyCode.get()
                            .setText("剩余：" + ((time + 15) / 1000) + "s");
                }
            }

            @Override
            public void onFinish() {
                if (null == tvVerifyCode.get()) {
                    this.cancel();
                    return;
                }
                tvVerifyCode.get().setEnabled(true);
                tvVerifyCode.get().setText(defaultString);
            }
        }.start();
    }
}
