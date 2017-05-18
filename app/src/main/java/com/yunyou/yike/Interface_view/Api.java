package com.yunyou.yike.Interface_view;

import com.yunyou.yike.entity.BannerData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public interface Api {
    /**
     * 22、登录：
     * ulogin
     * 传入：mobile  password  device_token
     */
//    @GET("ulogin/{mobile}/{password}/{device_token}")
//    Call<ResponseBody> ulogin(@Path("mobile") String mobile, @Path("password") String password
//            , @Path("device_token") String device_token);
//    @GET("ulogin")
//    Call<ResponseBody> ulogin(@QueryMap Map<String,String> params);
    @FormUrlEncoded
    @POST("ulogin")
    Call<ResponseBody> ulogin(@Header("token")String token, @Field("mobile") String mobile, @Field("password") String password
            , @Field("device_token") String device_token);

    /**
     * 1、首页banner图
     http://120.27.118.19:902/index.php/api/user/index_banner/
     * @return
     */
    @GET("index_banner/")
    Call<BannerData> index_banner();
}
