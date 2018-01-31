package com.app.ecosurvey.ui.Presenter;

import android.content.Context;

import com.app.ecosurvey.api.ApiRequestHandler;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.ui.Model.Request.PhotoRequest;
import com.app.ecosurvey.ui.Model.Request.SurveyPhotoRequest;
import com.app.ecosurvey.ui.Model.Request.VideoRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ListSurveyRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostSurveyRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.squareup.otto.Bus;

import java.util.HashMap;

import javax.inject.Inject;

public class MainPresenter {

    @Inject
    ApiRequestHandler apiRequestHandler;

    @Inject
    Bus bus;

    public MainPresenter(Context context) {
        MainApplication.component(context).inject(this);
    }

    public void onLoginRequest(com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest data) {
        apiRequestHandler.onLoginRequest(data);
    }

    public void onTokenRequest(TokenRequest data) {
        apiRequestHandler.onTokenRequest(data);
    }

    public void onUserInfoRequest(UserInfoRequest data) {
        apiRequestHandler.onUserInfoRequest(data);
    }

    public void onPostSurvey(PostSurveyRequest dicMap) {
        apiRequestHandler.onPostSurveyRequest(dicMap);
    }

    public void onListSurveyRequest(ListSurveyRequest data) {
        apiRequestHandler.onListSurveyRequest(data);
    }

    public void onCategoryRequest(CategoryRequest data) {
        apiRequestHandler.onCategoryRequest(data);
    }

    public void onSurveyPhotoRequest(SurveyPhotoRequest data) {
        //apiRequestHandler.onSurveyPhotoRequest(data);
    }

    public void onChecklistRequest(ChecklistRequest data){
        apiRequestHandler.onChecklistRequest(data);
    }

    public void onPhotoRequest(PhotoRequest data){
        apiRequestHandler.onPhotoRequest(data);
    }

    public void onVideoRequest(VideoRequest data){
        apiRequestHandler.onVideoRequest(data);
    }



    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}
