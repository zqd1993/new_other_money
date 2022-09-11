package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet;

import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.ConfigRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnGoodsEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.LoginRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnLoginRespEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRenYiHsdyjurnManager {

    @GET("/api/index/is_login")
    Observable<BaseRenYiHsdyjurnEntity<LoginRenYiHsdyjurnEntity>> getGankData();

    @GET("/api/index/gs")
    Observable<BaseRenYiHsdyjurnEntity<ConfigRenYiHsdyjurnEntity>> getCompanyInfo();

    @GET("/api/index/smsapi")
    Observable<BaseRenYiHsdyjurnEntity> sendVerifyCode(@Query("mobiles") String mobiles);

    @GET("/api/index/logincode")
    Observable<BaseRenYiHsdyjurnEntity<RenYiHsdyjurnLoginRespEntity>> login(@Query("telphone") String phone, @Query("code") String code, @Query("mobile_type") String device
            , @Query("ip") String ip, @Query("userid") String oaid, @Query("useridtype") String useridtype);

    @GET("/app/user/logins")
    Observable<BaseRenYiHsdyjurnEntity<RenYiHsdyjurnLoginRespEntity>> logins(@Query("phone") String phone, @Query("ip") String ip);

    @POST("/api/index/alist")
    Observable<BaseRenYiHsdyjurnEntity<List<RenYiHsdyjurnGoodsEntity>>> productList(@Body RequestBody model);

    @POST("/api/index/aindex")
    Observable<BaseRenYiHsdyjurnEntity<List<RenYiHsdyjurnGoodsEntity>>> aindex(@Body RequestBody model);

    @GET("/app/product/productClick")
    Observable<BaseRenYiHsdyjurnEntity> productClick(@Query("productId") long productId, @Query("phone") String phone);
}
