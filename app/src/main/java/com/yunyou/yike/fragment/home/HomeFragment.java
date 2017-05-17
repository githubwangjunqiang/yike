package com.yunyou.yike.fragment.home;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.Interface.Api;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.activity.PermissionActivity;
import com.yunyou.yike.activity.WebViewActivity;
import com.yunyou.yike.banner.GlideImageLoader;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Bean;
import com.yunyou.yike.fragment.home.imP.HomePresenter;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class HomeFragment extends BaseMainFragment implements IView.IHomeFragmentView {
    private BannerData mBannerData;//轮播图 的实体类


    /**
     * 构造方法
     */
    public HomeFragment() {
    }

    /**
     * 传参构造方法
     *
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected IPrenester setIPrenester() {
        return new HomePresenter(this);
    }

    private Banner banner;

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        banner = (Banner) view.findViewById(R.id.banner_home);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.ZoomOut);
        banner.setDelayTime(3000);  //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    protected void setlistener() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                WebViewActivity.startWebViewActivity(getContext(), mBannerData.getData().get(position)
                        .getSlide_url(), null);
            }
        });
    }


    private void toCall3(Api api) {
        Map<String, String> map = new ArrayMap<>();
        map.put("mobile", "15075818555");
        map.put("password", "123456");
        map.put("device_token", "123456");
        api.ulogin("123456", "15075818555", "123456", "123456").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    @Override
    public void showBanner(BannerData list) {
        mBannerData = list;
        List<BannerData.DataBean> data = mBannerData.getData();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list1.add(Bean.image + data.get(i).getSlide_pic());
        }
        banner.setImages(list1);
        banner.isAutoPlay(true);
        banner.start();
    }

    @Override
    public void startRefresh(Object object) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,//读sd卡
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写sd卡
                Manifest.permission.CAMERA,//相机
                Manifest.permission.ACCESS_FINE_LOCATION,//访问精细位置
                Manifest.permission.ACCESS_COARSE_LOCATION,//访问粗略位置
        };
        PermissionActivity.startPermissionActivity(permissions, new PermissionListener() {
            @Override
            public void success() {
                ((IPrenester.IHomeFragmentPrenester) mIPrenester).getBanner();
            }

            @Override
            public void error(String[] permission) {
                for (int i = 0; i < permission.length; i++) {
                    switch (permission[i]) {
                        case Manifest.permission.READ_EXTERNAL_STORAGE://读取存储卡
                            LogUtils.d("读取存储卡");
                            break;
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE://写sd卡
                            LogUtils.d("写sd卡");
                            break;
                        case Manifest.permission.CAMERA://相机
                            LogUtils.d("相机");
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION://访问精细位置
                            LogUtils.d("访问精细位置");
                            break;
                        case Manifest.permission.ACCESS_COARSE_LOCATION://访问粗略位置
                            LogUtils.d("访问粗略位置");
                            break;
                    }
                }
                To.ee("您关闭了 重要权限 会影响您的使用");
            }
        });
    }

    @Override
    public void showLoodingView(Object object) {

    }

    @Override
    public void showContentView(Object object) {

    }

    @Override
    public void showErrorView(Object object) {

    }

    @Override
    public void showEmptyView(Object object) {

    }

    @Override
    public void showNoNetworkView(Object object) {

    }

    @Override
    public void showTimeErrorView(Object object) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (banner != null) {
                banner.stopAutoPlay();
            }
        } else {
            if (banner != null && mBannerData != null) {
                banner.startAutoPlay();
            }
        }

    }

}
