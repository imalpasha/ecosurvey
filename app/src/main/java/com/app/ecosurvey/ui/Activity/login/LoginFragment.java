package com.app.ecosurvey.ui.Activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Activity.homepage.TabActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;

    @Bind(R.id.txtAuthID)
    TextView txtAuthID;

    @Bind(R.id.txtAuthIDHint)
    TextView txtAuthIDHint;

    @Bind(R.id.txtAuthPassword)
    TextView txtAuthPassword;

    @Bind(R.id.txtPasswordHint)
    TextView txtPasswordHint;

    @Bind(R.id.btnLogin)
    Button btnLogin;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    View view;
    String role;
    String token;

    public static LoginFragment newInstance(Bundle bundle) {

        LoginFragment fragment = new LoginFragment();
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

        view = inflater.inflate(R.layout.login_page, container, false);
        ButterKnife.bind(this, view);
        //pref = new SharedPrefManager(getActivity());

        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);

        editTextMaterial(txtAuthID, txtAuthIDHint);
        editTextMaterial(txtAuthPassword, txtPasswordHint);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getActivity(), TabActivity.class);
                //getActivity().startActivity(intent);

                initiateLoading(getActivity());

                String token = preferences.getString("temp_token", "DEFAULT");

                Log.e("token", token);

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setIcnumber(txtAuthID.getText().toString());
                loginRequest.setPassword(txtAuthPassword.getText().toString());
                loginRequest.setToken(token);
                presenter.onLoginRequest(loginRequest);

            }
        });

        return view;
    }


    @Subscribe
    public void onLoginReceive(LoginReceive loginReceive) {

        //dismissLoading();

        if (loginReceive.getApiStatus().equalsIgnoreCase("Y")) {
            token = loginReceive.getData().getToken();
            try {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("just_login", true);
                editor.putString("user_id", txtAuthID.getText().toString());
                editor.apply();

                //get_categories
                //CategoryRequest categoryRequest = new CategoryRequest();
                //presenter.onCategoryRequest(categoryRequest);

/*<<<<<<< HEAD
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("ROLE", loginReceive.getData().getUser().getRole());
                Log.e("ROLE", loginReceive.getData().getUser().getRole());
                getActivity().startActivity(intent);
                getActivity().finish();
=======*/
                UserInfoRequest userInfoRequest = new UserInfoRequest();
                userInfoRequest.setToken(loginReceive.getData().getToken());
                userInfoRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/user/" + txtAuthID.getText().toString());
                presenter.onUserInfoRequest(userInfoRequest);


/*>>>>>>> 8bce5b31c93b8ceccb4a25b24f10cde1f59c302e*/

            } catch (Exception e) {
                e.printStackTrace();
                dismissLoading();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            dismissLoading();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("auth_status", false);
            editor.apply();

            String error_msg = loginReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    @Subscribe
    public void onUserInfoReceive(UserInfoReceive userInfoReceive) {

        if (userInfoReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //save to realm
            //convert to gsom
            Gson gson = new Gson();
            String userInfo = gson.toJson(userInfoReceive);

            rController.saveUserInfo(context, userInfo);
            role = userInfoReceive.getData().getRole();

            ChecklistRequest checklistRequest = new ChecklistRequest();
            checklistRequest.setToken(token);
            checklistRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/checklist/submission/" + txtAuthID.getText().toString());
            presenter.onChecklistRequest(checklistRequest);


            /*Log.e("whaTrole",userInfoReceive.getData().getRole());
            Intent intent = new Intent(getActivity(), TabActivity.class);
            intent.putExtra("ROLE", userInfoReceive.getData().getRole());
            getActivity().startActivity(intent);
            getActivity().finish();*/

        } else {

            String error_msg = userInfoReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }

    @Subscribe
    public void onChecklistReceive(ChecklistReceive checklistReceive) {

        if (checklistReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //save to realm
            //convert to gsom
            Gson gson = new Gson();
            String checklist = gson.toJson(checklistReceive);

            rController.saveChecklist(context, checklist);

            Log.e("whaTrole",role);
            Intent intent = new Intent(getActivity(), TabActivity.class);
            intent.putExtra("ROLE", role);
            getActivity().startActivity(intent);
            getActivity().finish();

        } else {

            String error_msg = checklistReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }

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

