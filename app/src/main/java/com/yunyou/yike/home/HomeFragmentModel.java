package com.yunyou.yike.home;

import com.yunyou.yike.Interface.Api;
import com.yunyou.yike.Interface.IModel;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Bean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${王俊强} on 2017/4/24.
 */

public class HomeFragmentModel implements IModel.IHomeFragmentModel {

    @Override
    public void getBanner(IModel.AsyCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Bean.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);
        api.index_banner().enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(Call<BannerData> call, Response<BannerData> response) {
                if (response.body().getStatus() == 1) {
                    List<String> list = new ArrayList<String>();
                    list.add("http://img2.3lian.com/2014/f2/45/d/11.jpg");
                    list.add("http://img2.3lian.com/2014/f2/37/d/36.jpg");
                    list.add("http://pic1.win4000.com/pic/a/8e/05de474626.jpg");
                    list.add("http://file.mumayi.com/forum/201307/19/151555gg4x4naxt0j4nghn.jpg");
                    callback.success(list);
                } else {
                    callback.error(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<BannerData> call, Throwable t) {
                callback.error(t.getMessage());
            }
        });
    }
}
