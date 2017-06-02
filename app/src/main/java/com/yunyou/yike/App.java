package com.yunyou.yike;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yunyou.yike.dagger2.AppCompcoent;
import com.yunyou.yike.dagger2.DaggerAppCompcoent;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public class App extends Application {
    public static LocationClient mLocationClient;
    public static MyLocationListener mMyLocationListener;
    public static BDLocation mBDLocation;//



    /**
     * 全局上下文
     */
    private static Context context;
    private AppCompcoent mAppCompcoent;//全局app 依赖注入


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(this);
        mAppCompcoent = DaggerAppCompcoent.create();
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        initLocation(1000 * 60);
        mLocationClient.registerLocationListener(mMyLocationListener);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Context getContext() {
        return context;
    }

    private static String userId;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        App.userId = userId;
    }

    public AppCompcoent getAppCompcoent() {
        return mAppCompcoent;
    }

    public static LocationClient getLocationClient() {
        return mLocationClient;
    }

    public void setLocationClient(LocationClient locationClient) {
        mLocationClient = locationClient;
    }

    /**
     * 百度地图设置
     *
     * @param time
     */
    public void initLocation(int time) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = time;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，
        // 默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 百度地图 监听器
     */
    public static class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {


            if (location.getLocType() == BDLocation.TypeGpsLocation) {//gps
                setBDLocation(location);
                EventBus.getDefault().post(new EventBusMessage(EventBusMessage.LOCATION));
                LogUtils.d("gps定位成功=" + location.getLocationDescribe() + "-" + location.getLatitude() + "-" +
                        location.getLongitude());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//网络定位
                setBDLocation(location);
                EventBus.getDefault().post(new EventBusMessage(EventBusMessage.LOCATION));
                LogUtils.d("网络定位=" + location.getLocationDescribe() + "-" + location.getLatitude() + "-" +
                        location.getLongitude());
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {//离线定位
                setBDLocation(location);
                EventBus.getDefault().post(new EventBusMessage(EventBusMessage.LOCATION));
                LogUtils.d("离线定位=" + location.getLocationDescribe() + "-" + location.getLatitude() + "-" +
                        location.getLongitude());
            } else if (location.getLocType() == BDLocation.TypeServerError) {//服务端定位失败
                To.ee("服务端定位失败，请保持网络通畅");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {//网络不通畅定位失败
                To.ee("网络不通畅定位失败，请保持网络通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {//手机原因定位失败
                To.ee("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                To.ee("定位失败");
                LogUtils.e("定位失败" + location.getLocType());
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            LogUtils.e(s + "-" + i);
        }
    }

    @Override
    public void onTerminate() {
        LogUtils.d("程序终止的时候执行");
        appOut();
        super.onTerminate();
    }

    /**
     * 退出程序时调用
     */
    public static void appOut() {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mMyLocationListener = null;
        mLocationClient = null;
    }

    @Override
    public void onLowMemory() {
        LogUtils.d("低内存的时候执行");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtils.d("程序在内存清理的时候执行");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.d("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
    public static BDLocation getBDLocation() {
        return mBDLocation;
    }

    public static void setBDLocation(BDLocation BDLocation) {
        mBDLocation = BDLocation;
    }
}
