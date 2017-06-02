package com.yunyou.yike.utils;

import android.text.TextUtils;

import com.yunyou.yike.entity.BaseSort;
import com.yunyou.yike.http.cconstant.RxHttpConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/27.
 */

public class CallPostUtils {
    private Builder mBuilder;

    private CallPostUtils(Builder builder) {
        this.mBuilder = builder;
    }


    public String[] sort() {
        String[] strings = new String[2];

        Collections.sort(mBuilder.mBaseSorts, new Comparator<BaseSort>() {
            @Override
            public int compare(BaseSort o1, BaseSort o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mBuilder.mBaseSorts.size(); i++) {
            stringBuilder.append(mBuilder.mBaseSorts.get(i).getContent());
        }
        Long time = new Date().getTime();
        stringBuilder.append(time).append(RxHttpConstant.KEY);
        LogUtils.d(stringBuilder.toString());
        String sign = MD5Utils.md5Code(stringBuilder.toString());
        LogUtils.d("加密=" + sign);
        if (TextUtils.isEmpty(sign)) {
            return null;
        }
        strings[0] = String.valueOf(time);
        strings[1] = sign;
        if (TextUtils.isEmpty(strings[0]) || TextUtils.isEmpty(strings[1])) {
            return null;
        }
        return strings;
    }

    /**
     * 创建Builder 用来 返回链式调用
     *
     * @return
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 静态内部类 Builder 用来链式创建 MyOkHttpClent
     */
    public static class Builder {
        private List<BaseSort> mBaseSorts;

        private Builder() {
        }

        /**
         * 链式调用完毕
         *
         * @return 返回 MyOkHttpClent对象
         */
        public CallPostUtils build() {
            return new CallPostUtils(this);
        }


        /**
         * 添加头header方法
         *
         * @return Builder
         */
        public Builder addParamt(String key, Object vaLue) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("Parameter key cannot be null(排序签名的key是空的)");
            } else if (vaLue == null) {
                return this;
            } else {
                if (mBaseSorts == null) {
                    mBaseSorts = new ArrayList<>();
                }
                mBaseSorts.add(new BaseSort(key, vaLue));
            }
            return this;
        }

    }


}
