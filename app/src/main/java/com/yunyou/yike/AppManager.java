package com.yunyou.yike;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yunyou.yike.RongIM.RongIMConnentListener;
import com.yunyou.yike.RongIM.RongIMLoginManager;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;

/**
 * Created by ${王俊强} on 2017/6/8.
 */

public class AppManager {
    private static AppManager ourInstance;//管理类 对象
    private Context mContext;


    private String cityID;//当前城市


    private LocationClient mLocationClient;//百度地图 定位对象
    private MyLocationListener mMyLocationListener;//百度地图定位监听器
    private BDLocation mBDLocation;//定位成功后的位置信息


    public static AppManager getInstance() {
        return ourInstance;
    }

    /**
     * 初始化 app 管理类
     */
    public static void initApp(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppManager(context);
        }
    }

    /**
     * 私有构造器
     */
    private AppManager(Context context) {
        this.mContext = context;
        initBaiduMap();
        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mContext);
        //Fresco加载图片框架
        Fresco.initialize(mContext);
        //初始化融云管理类
        initRongIM(mContext);
    }


    /**
     * 退出程序
     */
    public void logOutApp() {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mMyLocationListener = null;
        mLocationClient = null;
        ourInstance = null;
        RongIMLoginManager.getInstance().disConnectRongIM(false);
    }

    /**
     * 初始化融云
     */
    private void initRongIM(Context context) {
        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(context.getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            LogUtils.d("融云初始化");
            RongIMLoginManager.getInstance().init(context);
        }
    }

    /**
     * 登陆融云
     */
    public void loginRongIM(String rRongImToken) {
        if (TextUtils.isEmpty(rRongImToken)) {
            To.oo("融云token 不能为空");
            return;
        }
        RongIMLoginManager.getInstance().connect(rRongImToken, new RongIMConnentListener() {
            @Override
            public void onTokenIncorrect() {
                EventBus.getDefault().post(new EventBusMessage(EventBusMessage.RONGIMTOKENINCORRECT));
            }

            @Override
            public void onSuccess(String userid) {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e("融云登录失败=" + errorCode.getMessage() + "-" + errorCode.getValue());
//                Reference<Context> reference = new WeakReference<Context>(ActivityCollector.getTopActivity());
//                if (reference.get() == null) {
//                    To.oo(mContext.getString(R.string.rongim_incorrect));
//                    return;
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(reference.get());
//                builder.setTitle(R.string.app_name_tixingnin);
//                builder.setMessage(R.string.rongim_incorrect);
//                builder.show();
            }
        });
    }


    /**
     * 初始化百度地图 定位服务
     */
    private void initBaiduMap() {
        mLocationClient = new LocationClient(mContext);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.setLocOption(initLocation(1000 * 60));
        mLocationClient.registerLocationListener(mMyLocationListener);
    }

    /**
     * 开始百度定位
     */
    public void startLocationClient() {
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        } else {
            initBaiduMap();
            startLocationClient();
        }
    }


    /**
     * 百度地图设置
     *
     * @param time
     */
    public LocationClientOption initLocation(int time) {
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
        return option;
    }


    /**
     * 百度地图 监听器
     */
    public class MyLocationListener implements BDLocationListener {

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
                To.oo("服务端定位失败，请保持网络通畅");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {//网络不通畅定位失败
                To.oo("网络不通畅定位失败，请保持网络通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {//手机原因定位失败
                To.oo("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                To.oo("定位失败");
                LogUtils.e("定位失败" + location.getLocType());
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            LogUtils.e(s + "-" + i);
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getCityID() {
        return cityID;
    }

    /**
     * 获取城市名称
     *
     * @return
     */
    public String getCityName() {
        if (mBDLocation == null) {
            return null;
        }
        String city = mBDLocation.getCity();
        if (TextUtils.isEmpty(city)) {
            return null;
        }
        int indexOf = city.lastIndexOf("市");
        if (indexOf != -1) {
            city = city.substring(0, indexOf);
            if (TextUtils.isEmpty(city)) {
                return null;
            }
        }
        return city;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public BDLocation getBDLocation() {
        return mBDLocation;
    }

    public void setBDLocation(BDLocation BDLocation) {
        mBDLocation = BDLocation;
    }

    public LocationClient getLocationClient() {
        return mLocationClient;
    }

}
