package com.app.ecosurvey.api;

import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ListSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.PostSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Receive.PhotoReceive;
import com.app.ecosurvey.ui.Model.Receive.SurveyPhotoReceive;
import com.app.ecosurvey.ui.Model.Receive.VideoReceive;
import com.app.ecosurvey.ui.Model.Request.SurveyPhotoRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.Content;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostSurveyRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

public interface ApiService {

    @POST("/api/v1/authenticate")
    Call<LoginReceive> login(@Body LoginRequest obj, @Header("Authorization") String header);

    @POST("/api/token")
    Call<TokenReceive> token(@Body TokenRequest obj);

    @GET("/api/v1/categories")
    Call<CategoryReceive> category(@Header("Authorization") String header);

    //@GET("/api/v1/categories")
    //Call<UserInfoReceive> userinfo(@Header("Authorization") String header, @Body  obj);

    @GET
    Call<UserInfoReceive> userinfo(@Header("Authorization") String header, @Url String url);

    @GET
    Call<ChecklistReceive> checklist(@Header("Authorization") String header, @Url String Url);

    @GET
    Call<PhotoReceive> photoRequest(@Header("Authorization") String header, @Url String Url);

    @GET
    Call<VideoReceive> videoRequest(@Header("Authorization") String header, @Url String Url);

    @FormUrlEncoded
    @POST("/api/v1/surveys")
    Call<PostSurveyReceive> postSurvey(
            @Field("IcNumber") String IcNumber,
            @Field("locationCode") String locationCode,
            @Field("locationName") String locationName,
            @Field("locationType") String locationType,
            @Field("content[]") String content,
            @Header("Authorization") String header
    );


    @GET
    Call<ListSurveyReceive> listSurvey(@Header("Authorization") String header, @Url String url);


    //surveyPhoto
    @Multipart
    @POST("upload")
    Call<SurveyPhotoReceive> surveyPhoto(
            @Header("Authorization") String header,
            @Part("icnumber") String icnumber,
            @Part("locationCode") String locationCode,
            @Part("locationName") String locationName,
            @Part("photos[]") List<MultipartBody.Part> files);

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


