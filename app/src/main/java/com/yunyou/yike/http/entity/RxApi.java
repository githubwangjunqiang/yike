package com.yunyou.yike.http.entity;


import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ${王俊强} on 2017/4/19.
 */

public interface RxApi {
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
    Call<ResponseBody> ulogin(@Url String s, @Header("token") String token, @Field("mobile") String mobile, @Field("password") String password
            , @Field("device_token") String device_token);

    /**
     * 1、首页banner图
     * http://120.27.118.19:902/index.php/api/user/index_banner/
     *
     * @return
     */
    @GET("index.php/api/user/index_banner/")
    Observable<JsonObject> index_banner();

    /**
     * 注册
     * url   |        http://120.27.118.19:902/index.php/api/user/reg
     * <p>
     * 参数         注释
     * mobile         手机号
     * password                密码
     * password2         确认密码
     * code                 验证码
     * time           请求接口时间
     * sign   签名
     */
    @FormUrlEncoded
    @POST("index.php/api/user/reg")
    Observable<JsonObject> register(@Field("mobile") String mobile, @Field("password") String password,
                                    @Field("password2") String password2, @Field("code") String code
            , @Field("time") String time, @Field("sign") String sign);

    /**
     * 接口名称    3  |   发送验证码
     * url         |      http://120.27.118.19:902/index.php/api/mobileverify/send
     * <p>
     * 参数       |        注释
     * mobile     |    手机号
     */
    @FormUrlEncoded
    @POST("index.php/api/mobileverify/send")
    Observable<JsonObject> send(@Field("mobile") String mobile);

    /**
     * 接口名称  2      登录
     * url       http://120.27.118.19:902/index.php/api/user/login
     * 参数         注释
     * mobile       手机号
     * password      密码
     * device_num      设备号
     * j_du        经度
     * w_du       维度
     * time       请求接口时间
     */
    @FormUrlEncoded
    @POST("index.php/api/user/login")
    Observable<JsonObject> login(@Field("mobile") String mobile, @Field("password") String password,
                                 @Field("device_num") String device_num, @Field("j_du") String j_du,
                                 @Field("w_du") String w_du, @Field("time") String time,
                                 @Field("sign") String sign);


    /**
     * 接口名称     7
     * 获取首页两排地址信息     http://120.27.118.19:902/index.php/api/user/get_double_addr
     */
    @GET("index.php/api/user/get_double_addr")
    Observable<JsonObject> get_double_addr();

    /**
     * 接口名称   6
     * 获取字母排序地址    http://120.27.118.19:902/index.php/api/user/region_info
     */
    @GET("index.php/api/user/region_info")
    Observable<JsonObject> region_info();
}
