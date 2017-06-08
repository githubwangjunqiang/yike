package com.yunyou.yike.RongIM;

import io.rong.imlib.RongIMClient;

/**
 * Created by ${王俊强} on 2017/6/6.
 */

public interface RongIMConnentListener {
    void onTokenIncorrect();

    void onSuccess(String userid);

    void onError(RongIMClient.ErrorCode errorCode);
}
