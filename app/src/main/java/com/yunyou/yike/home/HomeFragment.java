package com.yunyou.yike.home;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.Interface.Api;
import com.yunyou.yike.Interface.IPrenester;
import com.yunyou.yike.Interface.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.banner.GlideImageLoader;
import com.yunyou.yike.home.imP.HomePresenter;
import com.yunyou.yike.utils.To;

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
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private List<String> mStringList;


    /**
     * 构造方法
     */
    public HomeFragment() {
    }

    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
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
    public void showBanner(List<String> list) {
        mStringList = list;
        banner.setImages(list);
        banner.isAutoPlay(true);
        banner.start();
    }

    @Override
    public void startRefresh(Object object) {
        new RxPermissions(getActivity()).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        ((IPrenester.IHomeFragmentPrenester) mIPrenester).getBanner();
                    } else {
                        To.ee("您关闭了 重要权限 会影响您的使用");
                    }
                });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            if (banner != null) banner.stopAutoPlay();
            else if (banner != null && mStringList != null) banner.startAutoPlay();
    }

}
