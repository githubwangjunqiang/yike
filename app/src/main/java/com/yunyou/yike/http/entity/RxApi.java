package com.yunyou.yike.http.entity;


import com.yunyou.yike.entity.BannerData;
import com.yunyou.yike.entity.SendCode;
import com.yunyou.yike.entity.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Call<ResponseBody> ulogin(@Header("token") String token, @Field("mobile") String mobile, @Field("password") String password
            , @Field("device_token") String device_token);

    /**
     * 1、首页banner图
     * http://120.27.118.19:902/index.php/api/user/index_banner/
     *
     * @return
     */
    @GET("index_banner/")
    Call<BannerData> index_banner();

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
    Observable<User> register(@Field("mobile") String mobile, @Field("password") String password,
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
    Observable<SendCode> send(@Field("mobile") String mobile);

    /**
     * 接口名称  2                      |               登录
     * url                              |               http://120.27.118.19:902/index.php/api/user/login
     * <p>
     * 参数                             |               注释
     * mobile                           |               手机号
     * password                         |               密码
     * device_num                       |               设备号
     * j_du                             |               经度
     * w_du                             |               维度
     * time                             |          请求接口时间
     */
    @FormUrlEncoded
    @POST("index.php/api/user/login")
    Observable<SendCode> login(@Field("mobile") String mobile,@Field("password") String password,
                               @Field("device_num") String device_num,@Field("j_du") String j_du,
                               @Field("w_du") String w_du,@Field("time") String time,
                               @Field("sign") String sign);
}
