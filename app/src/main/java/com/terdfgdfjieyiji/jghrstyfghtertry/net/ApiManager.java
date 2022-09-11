package com.terdfgdfjieyiji.jghrstyfghtertry.net;

import com.terdfgdfjieyiji.jghrstyfghtertry.entity.BaseEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.ConfigEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.GoodsEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.LoginEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.LoginRespEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiManager {

    @GET("/api/index/is_login")
    Observable<BaseEntity<LoginEntity>> getGankData();

    @GET("/api/index/gs")
    Observable<BaseEntity<ConfigEntity>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Observable<BaseEntity> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Observable<BaseEntity<LoginRespEntity>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Observable<BaseEntity<LoginRespEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Observable<BaseEntity<List<GoodsEntity>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Observable<BaseEntity<List<GoodsEntity>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Observable<BaseEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);
}
