package com.yunyou.yike.model;

import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.Bean;

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
    public void getBanner(final IModel.AsyCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Bean.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RxApi rxApi = retrofit.create(RxApi.class);
        rxApi.index_banner().enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(Call<BannerData> call, Response<BannerData> response) {
                if (response.isSuccessful()) {
                    BannerData body = response.body();
                    if (body != null && body.getRetcode() == 2000) {
                        callback.success(body);
                    } else {
                        callback.error(response.body().getMsg());
                    }
                } else {
                    callback.error(response.message());
                }

            }

            @Override
            public void onFailure(Call<BannerData> call, Throwable t) {
                callback.error(t.getMessage());
            }
        });
    }
}
