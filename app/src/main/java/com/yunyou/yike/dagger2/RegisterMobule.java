package com.yunyou.yike.dagger2;


import com.yunyou.yike.entity.Bean;
import com.yunyou.yike.http.entity.RxApi;
import com.yunyou.yike.presenter.RegisterActivityPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${王俊强} on 2017/5/23.
 */

@Module
public class RegisterMobule {

    @Provides
    @Singleton
    public RegisterActivityPresenter gprovidesRegisterActivityPresenter(RxApi mapi) {
        return new RegisterActivityPresenter(mapi);
    }

    @Provides
    @Singleton
    public RxApi providersRxApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Bean.url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        return retrofit.create(RxApi.class);
    }


    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        LoggingInterceptor logging = new LoggingInterceptor(new Logger());
        logging.setLevel(LoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging);
        return builder.build();
    }
}


