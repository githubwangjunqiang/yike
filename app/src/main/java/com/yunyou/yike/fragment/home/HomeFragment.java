package com.yunyou.yike.fragment.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.yunyou.yike.App;
import com.yunyou.yike.AppManager;
import com.yunyou.yike.BaseMVPFragment;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.activity.AddressActivity;
import com.yunyou.yike.activity.JobActivity;
import com.yunyou.yike.activity.LookingWorkersActivity;
import com.yunyou.yike.activity.PermissionActivity;
import com.yunyou.yike.activity.WebViewActivity;
import com.yunyou.yike.banner.GlideImageLoader;
import com.yunyou.yike.dagger2.DaggerHomeFragmentCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Bean;
import com.yunyou.yike.entity.CityId;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.presenter.HomePresenter;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class HomeFragment extends BaseMVPFragment<IView.IHomeFragmentView, HomePresenter>
        implements IView.IHomeFragmentView {
    private BannerData mBannerData;//轮播图 的实体类
    private LinearLayout mLoadPeoPle;//找工人
    private LinearLayout mLoadWorker;//找工作
    private TextView mTextViewAddres;//点击地址
    private TextView mTextViewEmty;//空数据

    @Inject
    HomePresenter mHomePresenter;

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


    private Banner banner;

    @Override
    protected void RogerMessage(EventBusMessage message) {

    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.home_layout;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null) {

        }
        banner = optainView(R.id.banner_home);
        mLoadPeoPle = optainView(R.id.home_load_gongren);
        mLoadWorker = optainView(R.id.home_load_worker);
        mTextViewEmty = optainView(R.id.home_tvkong);
        mTextViewAddres = optainView(R.id.home_address);
        if (AppManager.getInstance().getBDLocation() != null) {
            mTextViewAddres.setText(AppManager.getInstance().getBDLocation().getCity());
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.ZoomOut);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);

    }

    @Override
    protected void setlistener() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {//点击轮播图
                WebViewActivity.startWebViewActivity(getContext(), mBannerData.getData().get(position)
                        .getSlide_url(), null);
            }
        });
        mLoadPeoPle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击找工人
                Intent intent = new Intent(getContext(), LookingWorkersActivity.class);
                startActivity(intent);
            }
        });
        mLoadWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击找工作
                Intent intent = new Intent(getContext(), JobActivity.class);
                startActivity(intent);
            }
        });
        mTextViewAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击左上角地址
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void showBanner(BannerData list) {
        mTextViewEmty.setVisibility(View.GONE);
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
    public void showCityIDSuccess(Object cityID) {
        if (cityID != null && cityID instanceof CityId) {
            CityId city = (CityId) cityID;
            AppManager.getInstance().setCityID(city.getData().getId());
        }
    }

    @Override
    public void startRefresh(final boolean isShowLoadingView) {
        mTextViewEmty.setVisibility(View.VISIBLE);
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,//读sd卡
                Manifest.permission.WRITE_EXTERNAL_STORAGE,//写sd卡
//                Manifest.permission.CAMERA,//相机
        };
        PermissionActivity.startPermissionActivity(permissions, new PermissionListener() {
            @Override
            public void success() {
                mHomePresenter.getBanner(isShowLoadingView);
                String cityName = AppManager.getInstance().getCityName();
                if (cityName == null) {
                    To.oo("亲可能定位没有成功，请您刷新界面试试呢...");
                } else {
                    Map<String, String> maps = new ArrayMap<>();
                    maps.put("name", cityName);
                    mHomePresenter.getCityID(maps, isShowLoadingView);
                }
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
                    }
                }
                To.ee("您关闭了 重要权限 会影响您的使用");
            }
        });
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

    @Override
    protected HomePresenter mPresenterCreate() {
        DaggerHomeFragmentCompcoent.builder()
                .appCompcoent(((App) getActivity().getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule())
                .build().inJect(this);
        return mHomePresenter;
    }
}
