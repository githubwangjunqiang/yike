package com.yunyou.yike.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yunyou.yike.entity.BaseSort;
import com.yunyou.yike.http.cconstant.RxHttpConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ${王俊强} on 2017/5/27.
 */

public class CallPostUtils {
    private static final String TIME = "time";
    private static final String SIGN = "sign";
    private Builder mBuilder;

    private CallPostUtils(Builder builder) {
        this.mBuilder = builder;
    }

    public Map<String, String> getMap() {
        if (mBuilder == null || mBuilder.mMaps == null) {
            return null;
        }
        if (mBuilder.mMaps.isEmpty()) {
            return mBuilder.mMaps;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> map : mBuilder.mMaps.entrySet()) {
            stringBuilder.append(map.getValue());
        }
        Long time = new Date().getTime();
        stringBuilder.append(time).append(RxHttpConstant.KEY);
        LogUtils.d(stringBuilder.toString());
        String sign = MD5Utils.md5Code(stringBuilder.toString());
        LogUtils.d("加密=" + sign);
        if (TextUtils.isEmpty(sign)) {
            return null;
        }
        mBuilder.mMaps.put(TIME, String.valueOf(time));
        mBuilder.mMaps.put(SIGN, sign);

        return mBuilder.mMaps;

    }

    public String[] sort() {
        String[] strings = new String[2];
        if (mBuilder == null || mBuilder.mBaseSorts == null) {
            return null;
        }
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
        private Map<String, String> mMaps;

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
         * @return Builder
         */
        public Builder addParamt(String key, String vaLue) {
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

        /**
         * @return Builder
         */
        public CallPostUtils addMap(@NonNull Map<String, String> map) {
            if (map == null) {
                throw new NullPointerException("Parameter key cannot be null(排序签名的map是空的)");
            } else {
                if (mMaps == null) {
                    mMaps = new TreeMap<>(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                }
                mMaps.putAll(map);
            }
            return new CallPostUtils(this);
        }

    }


}
