package com.app.ecosurvey.api;

import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    @POST("/api/v1/authenticate")
    Call<LoginReceive> login(@Body LoginRequest obj,@Header("Authorization") String header);

    @POST("/api/token")
    Call<TokenReceive> token(@Body TokenRequest obj);

    @GET("/api/v1/categories")
    Call<CategoryReceive> category(@Header("Authorization") String header);

    //@GET("/api/v1/categories")
    //Call<UserInfoReceive> userinfo(@Header("Authorization") String header, @Body  obj);

    @GET
    Call<UserInfoReceive> userinfo(@Header("Authorization") String header,@Url String url);


}


