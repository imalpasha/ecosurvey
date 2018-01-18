package com.app.ecosurvey.api;

import android.content.Context;
import android.util.Log;

import com.app.ecosurvey.MainFragmentActivity;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequestHandler {

    @Inject
    ApiService apiService;

    @Inject
    Bus bus;

    Context act;

    public ApiRequestHandler(Context context) {
        MainApplication.get(context).getNetComponent().inject(this);
        act = context;
    }


    //call api function - retrofit2.1
    @Subscribe
    public void onTokenRequest(final TokenRequest event) {

        Call<TokenReceive> call = apiService.token(event);
        call.enqueue(new Callback<TokenReceive>() {

            //succces retrieve information
            @Override
            public void onResponse(Call<TokenReceive> call, Response<TokenReceive> response) {

                TokenReceive user = new TokenReceive();
                //successs call
                if (response.isSuccessful()) {
                    user = response.body();
                    user.setApiStatus("Y");
                    bus.post(new TokenReceive(user));
                } else {
                    //success call but with err message
                    user.setApiStatus("N");
                    user.setMessage("Err_Message");
                    bus.post(new TokenReceive(user));
                }
            }

            //failed to retreive information
            @Override
            public void onFailure(Call<TokenReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.connectionError(MainFragmentActivity.getContext());
                Log.e("SUCCESS", "DOUBLE_N");

            }
        });
    }


    //call api function - retrofit2.1
    @Subscribe
    public void onLoginRequest(final LoginRequest event) {

        Call<LoginReceive> call = apiService.login(event,"Bearer "+event.getToken());
        call.enqueue(new Callback<LoginReceive>() {

            //succces retrieve information
            @Override
            public void onResponse(Call<LoginReceive> call, Response<LoginReceive> response) {

                LoginReceive user = new LoginReceive();
                //successs call
                if (response.isSuccessful()) {
                    user = response.body();
                    user.setApiStatus("Y");
                    bus.post(new LoginReceive(user));
                } else {
                    //success call but with err message
                    user.setApiStatus("N");
                    user.setMessage("Err_Message");
                    bus.post(new LoginReceive(user));
                }
            }

            //failed to retreive information
            @Override
            public void onFailure(Call<LoginReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.connectionError(MainFragmentActivity.getContext());
                Log.e("SUCCESS", "DOUBLE_N");

            }
        });
    }

    //call api function - retrofit2.1
    @Subscribe
    public void onCategoryRequest(final CategoryRequest event) {

        Call<CategoryReceive> call = apiService.category("Bearer "+event.getToken(),event);
        call.enqueue(new Callback<CategoryReceive>() {

            //succces retrieve information
            @Override
            public void onResponse(Call<CategoryReceive> call, Response<CategoryReceive> response) {

                CategoryReceive user = new CategoryReceive();
                //successs call
                if (response.isSuccessful()) {
                    user = response.body();
                    user.setApiStatus("Y");
                    bus.post(new CategoryReceive(user));
                } else {
                    //success call but with err message
                    user.setApiStatus("N");
                    user.setMessage("Err_Message");
                    bus.post(new CategoryReceive(user));
                }
            }

            //failed to retreive information
            @Override
            public void onFailure(Call<CategoryReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.connectionError(MainFragmentActivity.getContext());
                Log.e("SUCCESS", "DOUBLE_N");

            }
        });
    }

    //call api function - retrofit2.1
    @Subscribe
    public void onUserInfoRequest(final UserInfoRequest event) {

        Call<UserInfoReceive> call = apiService.userinfo("Bearer "+event.getToken(),event.getUrl());
        call.enqueue(new Callback<UserInfoReceive>() {

            //succces retrieve information
            @Override
            public void onResponse(Call<UserInfoReceive> call, Response<UserInfoReceive> response) {

                UserInfoReceive user = new UserInfoReceive();
                //successs call
                if (response.isSuccessful()) {
                    user = response.body();
                    user.setApiStatus("Y");
                    bus.post(new UserInfoReceive(user));
                } else {
                    //success call but with err message
                    user.setApiStatus("N");
                    user.setMessage("Err_Message");
                    bus.post(new UserInfoReceive(user));
                }
            }

            //failed to retreive information
            @Override
            public void onFailure(Call<UserInfoReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.connectionError(MainFragmentActivity.getContext());
                Log.e("SUCCESS", "DOUBLE_N");

            }
        });
    }






}
