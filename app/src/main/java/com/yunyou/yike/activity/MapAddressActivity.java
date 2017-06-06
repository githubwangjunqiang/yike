package com.yunyou.yike.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.MapListAdapter;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.ui_view.dialog.DiaLogMapCity;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.PoiOverlay;
import com.yunyou.yike.utils.SizeUtil;
import com.yunyou.yike.utils.To;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/2.
 */

public class MapAddressActivity extends BaseActivity {
    private MapView mMapView = null;//百度地图view
    private BaiduMap mBaiduMap = null;
    private BitmapDescriptor bitmapDescriptor;//当前定位位置
    private LatLng mLatLng;
    private ImageView mImageViewCenter;
    private ObjectAnimator animator;//跳跃动画
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private Button mButtonOk;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private MapListAdapter mMapListAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private GeoCoder mGeoCoder;//正反向检索
    private PoiInfo mPoiInfo;//最终选择的地址
    private PoiSearch mPoiSearch;//正向检索
    private final String china = "中国";
    private LinearLayout mNewtonCradleLoading;

    @Override

    protected boolean beforeWindow(Bundle savedInstanceState) {
        super.beforeWindow(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        if (App.getBDLocation() == null) {
            To.ee("还没有定为成功呢亲");
            return true;
        }
        return false;
    }

    @Override
    public void startRefresh(Object object) {
        mTextViewTitle.setText(R.string.xuanzedizhi);
//        mBaiduMap.setTrafficEnabled(true);  //开启交通图
//        mBaiduMap.setBaidu/**/HeatMapEnabled(true);//开启热力图
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData data = new MyLocationData.Builder()
//                .direction(mCurrentX)
                .accuracy(App.getBDLocation().getRadius())
                .latitude(App.getBDLocation().getLatitude())
                .longitude(App.getBDLocation().getLongitude())
                .build();
        mBaiduMap.setMyLocationData(data);
        //自定义图标
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.wodeweizhi);
        MyLocationConfiguration myLocationConfiguration =
                new MyLocationConfiguration
                        (MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        mLatLng = new LatLng(App.getBDLocation().getLatitude(), App.getBDLocation().getLongitude());
        toMapAnition(mBaiduMap, mLatLng);
        LogUtils.d("类型=" + App.getBDLocation().getCoorType());
        mAppBarLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                toSearch(mBaiduMap.getMapStatus().target, null, china);
                mNewtonCradleLoading.setVisibility(View.GONE);
            }
        }, 1500);


    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_address_map;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mMapView = optionView(R.id.baidumapview_address);
        mImageViewCenter = optionView(R.id.map_iviamge);
        mImageViewBack = optionView(R.id.title_ivback);
        mNewtonCradleLoading = optionView(R.id.map_address_linglayout);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mAppBarLayout = optionView(R.id.map_appbatlayout);
        mButtonOk = optionView(R.id.title_btn);
        mRecyclerView = optionView(R.id.map_rectleview);
        mCoordinatorLayout = optionView(R.id.map_coordlayout);
        mSearchView = optionView(R.id.map_searchview);
        mCollapsingToolbarLayout = optionView(R.id.map_colllayout);
        mButtonOk.setVisibility(View.VISIBLE);
        mBaiduMap = mMapView.getMap();
        mGeoCoder = GeoCoder.newInstance();
        mPoiSearch = PoiSearch.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(new MyOnGetGeoCoderResultListener());
        mPoiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener());
        int identifierID = mSearchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView mSearchTextview = (TextView) mSearchView.findViewById(identifierID);
        mSearchTextview.setTextSize(13);
        mSearchTextview.setTextColor(ContextCompat.getColor(this, R.color.white));
        mSearchTextview.setHintTextColor(ContextCompat.getColor(this, R.color.background));
        mSearchTextview.setHint(R.string.shurudiming);


        startRefresh(null);
    }


    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchView.isIconified()) {
                    finish();
                } else {
                    mSearchView.setIconified(true);
                }
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                toMapAnition(mBaiduMap, latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                toMapAnition(mBaiduMap, mapPoi.getPosition());
                return false;
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                toSearch(mapStatus.target, null, china);
            }
        });
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击确定
                if (mPoiInfo != null) {
                    if (mPoiInfo.address == null) {
                        To.ee("选择地址失败呢亲，抱歉请重新选择吧");
                        return;
                    }
                    EventBus.getDefault().post(new EventBusMessage(EventBusMessage.MAPADDRESS, mPoiInfo));
                    finish();
                } else {
                    To.ss(mRecyclerView, "亲 ..快去点击拖动地图或者搜索地址呦");
                }
            }
        });
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewTitle.setVisibility(View.GONE);
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                toSearch(null, query, china);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mTextViewTitle.setVisibility(View.VISIBLE);
                mBaiduMap.clear();
                return false;
            }
        });
    }

    /**
     * 检索监听器
     */
    private class MyOnGetGeoCoderResultListener implements OnGetGeoCoderResultListener {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {//正向检
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                To.ee("没有检索到结果,请重试");
                return;
            }
            toMapAnition(mBaiduMap, result.getLocation());
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {//反向检
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                To.ee("没有检索到结果,请重试");
                return;
            }
            setPoiListInfo(result.getPoiList(), result);
        }
    }

    /**
     * 正向检索
     */
    private class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {

        @Override
        public void onGetPoiResult(PoiResult result) {//检索结果 包括 周边 附近 区域内 城市内
            if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                To.ee("亲，没有找到你输入的地址呢 ，请您检查地址是否有误呢");
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {//等于没有错误
                List<PoiInfo> allPoi = result.getAllPoi();
                if (allPoi == null || allPoi.isEmpty()) {
                    To.ee("亲，没有找到你输入的地址 ，请您检查地址是否有误呢");
                    return;
                }

                mBaiduMap.clear();
                PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result);
                overlay.addToMap();
                overlay.zoomToSpan();
                setPoiListInfo(allPoi, null);

            } else if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {//当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                List<CityInfo> suggestCityList = result.getSuggestCityList();
                if (suggestCityList == null || suggestCityList.isEmpty()) {
                    To.ee("亲，没有找到你输入的地址呢 ，请您检查地址是否有误呢");
                    return;
                }
                new DiaLogMapCity(MapAddressActivity.this, new DiaLogMapCity.CallBack() {
                    @Override
                    public void addFeel(String citys) {
                        toSearch(null, keyWord, citys);
                    }
                }, suggestCityList, keyWord).show();
            }


        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult result) {
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(MapAddressActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MapAddressActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                        .show();


            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            if (poiIndoorResult == null) {
                LogUtils.d("poiIndoorResult = null");
                return;
            }
        }
    }

    private String keyWord;

    /**
     * 开始检索
     *
     * @param mapStatus
     */
    private void toSearch(LatLng mapStatus, String keyWords, String city) {
        keyWord = keyWords;

        if (mapStatus == null && keyWords == null) {
            To.oo("请重试");
            return;
        }
        if (mMapListAdapter != null) {
            mMapListAdapter.showLoading();
        }
        mPoiInfo = null;

        if (mapStatus == null && keyWords != null) {
            PoiCitySearchOption bound = new PoiCitySearchOption();
            bound.city(city).keyword(keyWords).pageNum(0);
            mPoiSearch.searchInCity(bound);//城市内检索


//            GeoCodeOption option = new GeoCodeOption();
//            option.address(keyWords).city("北京");
//            mGeoCoder.geocode(option);//反向地理编码检索

        }
        if (keyWords == null && mapStatus != null) {
            ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
            mReverseGeoCodeOption.location(mapStatus);
            mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);//开始检索  反向检索
            startAnimationCenter(mImageViewCenter);
        }


        mRecyclerView.smoothScrollToPosition(0);
        mAppBarLayout.setExpanded(true, true);
    }

    /**
     * 处理检索结果
     */
    private void setPoiListInfo(@NonNull List<PoiInfo> poiList, ReverseGeoCodeResult result) {
        if (poiList == null) {
            return;
        }
        PoiInfo poiInfo = new PoiInfo();
        if (result != null) {
            poiInfo.city = result.getSematicDescription() == null ? "" : result.getSematicDescription();
            poiInfo.address = result.getAddress() == null ? "" : result.getAddress();
            poiInfo.location = result.getLocation() == null ? new LatLng(0.0, 0.0) : result.getLocation();
            poiList.add(0, poiInfo);
        }
        setAdapter(poiList);
    }

    /**
     * 设置适配器
     *
     * @param poiList
     */
    private void setAdapter(final List<PoiInfo> poiList) {
        mAppBarLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMapListAdapter != null) {
                    mMapListAdapter.setList(poiList);
                } else {
                    mMapListAdapter = new MapListAdapter(poiList, MapAddressActivity.this, new MapListAdapter.CallBack() {
                        @Override
                        public void onClick(LatLng latLng) {
                            toMapAnition(mBaiduMap, latLng);
                        }

                        @Override
                        public void onSuccess(PoiInfo poiInfo) {
                            mPoiInfo = poiInfo;
                        }
                    });
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MapAddressActivity.this));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(MapAddressActivity.this,
                            DividerItemDecoration.VERTICAL));
                    mRecyclerView.setAdapter(mMapListAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }
        }, 500);

    }


    /**
     * 地图移动中心点
     */
    private void toMapAnition(BaiduMap baiduMap, LatLng latLng) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(15.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 开启中心跳跃动画
     */
    private void startAnimationCenter(View view) {
        Reference<View> viewReference = new WeakReference<View>(view);
        float yy = viewReference.get().getTranslationY();
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(viewReference.get(),
                    "translationY", yy, -180f, yy, -40f, yy, -20f, yy);
            animator.setDuration(800);
        }
        if (animator.isRunning()) {
            return;
        }
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGeoCoder.destroy();
        mPoiSearch.destroy();
        mBaiduMap.clear();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    private boolean isFist = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFist) {
            isFist = false;
            ViewGroup.LayoutParams layoutParams = mCollapsingToolbarLayout.getLayoutParams();
            if (layoutParams != null) {
                int deviceHeight = SizeUtil.getDeviceHeight(App.getContext());

                int mheight = (deviceHeight - SizeUtil.getStatusBarHeight(this)) / 2;

                layoutParams.height = mheight;
                mCollapsingToolbarLayout.setLayoutParams(layoutParams);
            }
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
//            // if (poi.hasCaterDetails) {
//            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
//                    .poiUid(poi.uid));
//            // }
            toMapAnition(mBaiduMap, poi.location);

            return true;
        }
    }
}
