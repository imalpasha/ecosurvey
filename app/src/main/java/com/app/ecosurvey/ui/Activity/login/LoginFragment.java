package com.app.ecosurvey.ui.Activity.login;

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
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.LoginRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Activity.homepage.TabActivity;
import com.app.ecosurvey.utils.SharedPrefManager;
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


    View view;
    SharedPrefManager pref;

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

        dismissLoading();

        if (loginReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("just_login", true);
                editor.apply();

                //get_categories
                //CategoryRequest categoryRequest = new CategoryRequest();
                //presenter.onCategoryRequest(categoryRequest);

                Intent intent = new Intent(getActivity(), TabActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

            } catch (Exception e) {
                e.printStackTrace();

                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("auth_status", false);
            editor.apply();

            String error_msg = loginReceive.getMessage();
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

