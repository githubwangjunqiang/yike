package com.yunyou.yike.listener;

/**
 * Created by ${王俊强} on 2017/5/17.
 */

public interface PermissionListener {
    void success();
    void error(String[] permission);
}
