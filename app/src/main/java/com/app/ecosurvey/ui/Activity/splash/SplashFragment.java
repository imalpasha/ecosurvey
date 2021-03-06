package com.app.ecosurvey.ui.Activity.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ecosurvey.MainController;
import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.homepage.TabActivity;
import com.app.ecosurvey.ui.Activity.login.LoginActivity;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ListSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ListSurveyRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;

public class SplashFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    View view;
    SharedPrefManager pref;
    private String token;
    private String role;
    private Boolean proceed = true;
    private String user_token;

    public static SplashFragment newInstance(Bundle bundle) {

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.component(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.splash_page, container, false);
        ButterKnife.bind(this, view);

        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);
        //get_token
        user_token = preferences.getString("user_token", "DEFAULT");

        //if no internet.but user already login.
        //redirect to tab activity
        Log.e("Internet", Boolean.toString(MainController.connectionAvailable(getActivity())));
        Log.e("Login", Boolean.toString(preferences.getBoolean("just_login", false)));

        if (!MainController.connectionAvailable(getActivity()) && preferences.getBoolean("just_login", false)) {

            Realm realm = rController.getRealmInstanceContext(context);
            proceed = false;

            UserInfoCached survey = realm.where(UserInfoCached.class).findFirst();
            if (survey != null) {
                Gson gson = new Gson();
                UserInfoReceive userInfoReceive = gson.fromJson(survey.getUserInfoString(), UserInfoReceive.class);
                String role = userInfoReceive.getData().getRole();

                Log.e("Role", role);
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("ROLE", role);
                getActivity().startActivity(intent);
                getActivity().finish();
            } else {

            }

        } else if (MainController.connectionAvailable(getActivity()) && !preferences.getBoolean("just_login", false)) {
            proceed = true;
        } else {
            proceed = true;
        }


        if (proceed) {

            initiateLoading(getActivity());
            TokenRequest tokenRequest = new TokenRequest();
            presenter.onTokenRequest(tokenRequest);

        }


        //Intent intent = new Intent(getActivity(), LoginActivity.class);
        //getActivity().startActivity(intent);

        //SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("temp_token", "12345");
        //editor.apply();

        return view;
    }


    @Subscribe
    public void onTokenReceive(TokenReceive tokenReceive) {

        if (tokenReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                token = tokenReceive.getData().getToken();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("temp_token", token);
                editor.apply();


                //load category_daily
                //get_categories
                CategoryRequest categoryRequest = new CategoryRequest();
                categoryRequest.setToken(token);
                presenter.onCategoryRequest(categoryRequest);

            } catch (Exception e) {
                e.printStackTrace();
                dismissLoading();

                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = tokenReceive.getMessage();
            dismissLoading();

            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    @Subscribe
    public void onCategoryReceive(CategoryReceive categoryReceive) {

        if (categoryReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                //saved_category_into_realm
                rController.saveCategory(context, categoryReceive);

                if (preferences.getBoolean("just_login", false)) {

                    //get user data here.
                    loadProfile();


                } else {
                    dismissLoading();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }


            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
                dismissLoading();

            }

        } else {

            String error_msg = categoryReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
            dismissLoading();

        }


    }

    public void loadProfile() {

        //load_user_info
        //loadingSegment.setVisibility(View.VISIBLE);
        //btnRetry.setVisibility(View.GONE);

        String userId = preferences.getString("user_id", "");

        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setToken(user_token);
        userInfoRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/user/" + userId);
        presenter.onUserInfoRequest(userInfoRequest);

    }

    @Subscribe
    public void onUserInfoReceive(UserInfoReceive userInfoReceive) {

        if (userInfoReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //save to realm
            //save to role
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user_role", userInfoReceive.getData().getRole());
            editor.apply();

            //convert to gson
            Gson gson = new Gson();
            String userInfo = gson.toJson(userInfoReceive);

            String userId = preferences.getString("user_id", "");

            role = userInfoReceive.getData().getRole();

            rController.saveUserInfo(context, userInfo);

            //call existing survey
            ListSurveyRequest listSurveyRequest = new ListSurveyRequest();
            listSurveyRequest.setToken(user_token);
            listSurveyRequest.setUrl("/api/v1/surveys/" + userId);
            presenter.onListSurveyRequest(listSurveyRequest);


        } else {

            String error_msg = userInfoReceive.getMessage();
            setAlertDialog(getActivity(), error_msg, getString(R.string.err_title));
            dismissLoading();

        }
    }

    @Subscribe
    public void onListSurveyReceive(ListSurveyReceive listSurveyReceive) {


        if (listSurveyReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //update_realm_with_local
            try {
                rController.updateLocalRealm(getActivity(), listSurveyReceive);
            } finally {

                dismissLoading();

                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("ROLE", role);
                getActivity().startActivity(intent);
                getActivity().finish();

            }

        } else {

            String error_msg = listSurveyReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
            dismissLoading();

        }
    }

    /*@Subscribe
    public void onChecklistReceive(ChecklistReceive checklistReceive) {
        dismissLoading();
        if (checklistReceive.getApiStatus().equalsIgnoreCase("Y")) {

            try {
                //save to realm
                //convert to gsom
                Gson gson = new Gson();
                String checklist = gson.toJson(checklistReceive);

                rController.saveChecklist(context, checklist);

            } finally {
                //update_realm_with_local
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("ROLE", role);
                getActivity().startActivity(intent);
                getActivity().finish();

            }

        } else {

            String error_msg = checklistReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        bus.unregister(this);
    }

}

