package com.yunyou.yike.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.AddressHotAdapter;
import com.yunyou.yike.adapter.CityListAdapter;
import com.yunyou.yike.dagger2.DaggerAddressCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.AddressCity;
import com.yunyou.yike.entity.City;
import com.yunyou.yike.entity.CityList;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.presenter.AddressActivityPresenter;
import com.yunyou.yike.ui_view.listview.PinnedHeaderListView;
import com.yunyou.yike.ui_view.sidebar.ISideBarSelectCallBack;
import com.yunyou.yike.ui_view.sidebar.SideBar;
import com.yunyou.yike.utils.To;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class AddressActivity extends BaseMVPActivity<IView.IAddressActivityView,
        AddressActivityPresenter> implements IView.IAddressActivityView {
    @Inject
    AddressActivityPresenter mPresenter;
    private SearchView mSearchView;
    private PinnedHeaderListView mListViewCity;//城市列表
    private SideBar mSideBar;
    private CityListAdapter mCityListAdapter;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private GridView mGridView;
    private AddressHotAdapter mAddressHotAdapter;


    @Override
    public void startRefresh(Object object) {
        mPresenter.getAddressHot();
        mPresenter.getAddressCity();
    }

    @Override
    protected AddressActivityPresenter mPresenterCreate() {
        DaggerAddressCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mPresenter;
    }

    @Override
    public void showAddressHotData(AddressCity feelList) {
        mAddressHotAdapter = new AddressHotAdapter(this, feelList.getData());
        mGridView.setAdapter(mAddressHotAdapter);
    }


    @Override
    public void showAddressHotDataError(String string) {
        To.ss(mSearchView, string);
    }

    @Override
    public void showAddressCityData(City city) {
        List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"
                , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        List<CityList> list = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            CityList cityList = new CityList();
            cityList.setName(strings.get(i));
            List<CityList.Data> listss = new ArrayList<>();
            cityList.setList(listss);
            list.add(cityList);
        }
        for (int i = 0; i < city.getData().size(); i++) {
            City.DataBean dataBean = city.getData().get(i);
            String pingyin = dataBean.getPingyin();
            for (int j = 0; j < list.size(); j++) {
                if (TextUtils.equals(pingyin, list.get(j).getName())) {
                    list.get(j).getList().add(new CityList.Data(dataBean.getId(), dataBean.getName()));
                }
            }
        }


        mCityListAdapter = new CityListAdapter(list, this, mListViewCity);
        mListViewCity.setAdapter(mCityListAdapter);
    }

    @Override
    public void showAddressCityDataError(String string) {
        showErrorView(string);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_address;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.address_statslayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSearchView = optionView(R.id.address_searchview);

        int identifierID = mSearchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView mSearchTextview = (TextView) mSearchView.findViewById(identifierID);
        mSearchTextview.setTextColor(ContextCompat.getColor(this, R.color.text_color));
        mSearchTextview.setHintTextColor(ContextCompat.getColor(this, R.color.text_color_alp));
        mSearchTextview.setTextSize(13);
        mSideBar = optionView(R.id.address_sidebar);
        mListViewCity = optionView(R.id.pinnedheader_listview);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mGridView = optionView(R.id.address_gv);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle.setText(R.string.xuanzechengshi);
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
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//搜索按钮
            @Override
            public boolean onQueryTextSubmit(String query) {//
                if (mCityListAdapter != null) {
                    mCityListAdapter.searchKeyWord(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSideBar.setOnStrSelectCallBack(new ISideBarSelectCallBack() {//索引
            @Override
            public void onSelectStr(int index, String selectStr) {
                if (mCityListAdapter != null) {
                    int rightSection = 0;
                    for (int i = 0; i < index; i++) {
                        rightSection += mCityListAdapter.getCountForSection(i) + 1;
                    }
                    mListViewCity.setSelection(rightSection);
                }
            }
        });
        mListViewCity.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position,
                                    long id) {
                To.oo(section + "--" + position);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }


    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    public void onBackPressed() {
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
