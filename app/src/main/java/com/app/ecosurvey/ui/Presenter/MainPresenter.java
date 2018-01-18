package com.app.ecosurvey.ui.Presenter;

import android.content.Context;

import com.app.ecosurvey.api.ApiRequestHandler;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class MainPresenter {

    @Inject
    ApiRequestHandler apiRequestHandler;

    @Inject
    Bus bus;

    public MainPresenter(Context context) {
        MainApplication.component(context).inject(this);
    }

    public void onLoginRequest(LoginRequest data) {
        apiRequestHandler.onLoginRequest(data);
    }

    public void onTokenRequest(TokenRequest data) {
        apiRequestHandler.onTokenRequest(data);
    }

    public void onUserInfoRequest(UserInfoRequest data) {
        apiRequestHandler.onUserInfoRequest(data);
    }



    public void onCategoryRequest(CategoryRequest data) {
        apiRequestHandler.onCategoryRequest(data);
    }




    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

}
