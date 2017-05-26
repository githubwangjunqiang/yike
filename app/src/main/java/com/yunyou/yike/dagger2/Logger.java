package com.yunyou.yike.dagger2;


import com.yunyou.yike.utils.LogUtils;

/**
 * @author yuyh.
 * @date 2016/12/13.
 */
public class Logger implements LoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        LogUtils.w( message);
    }
}
