package com.app.ecosurvey.api;

import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ListSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.PostSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostSurveyRequest;
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


    @POST("/api/v1/surveys")
    Call<PostSurveyReceive> postSurvey(@Body PostSurveyRequest obj,@Header("Authorization") String header);

    @GET
    Call<ListSurveyReceive> listSurvey(@Header("Authorization") String header, @Url String url);

    /*@Multipart
    @POST("user/updateprofile")
    Observable<ResponseBody> updateProfile(@Part("user_id") RequestBody id, @Part("full_name") RequestBody fullName, @Part MultipartBody.Part image, @Part("other") RequestBody other);

    //pass it like this
    File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
    RequestBody requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file);

    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
            MultipartBody.Part.createFormData("image", file.getName(), requestFile);

    // add another part within the multipart request
    RequestBody fullName =
            RequestBody.create(
                    MediaType.parse("multipart/form-data"), "Your Name");

service.updateProfile(id, fullName, body, other)*/
}


