package com.app.ecosurvey.ui.Activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.survey.CategoryParlimenActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MySurveyFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    @Inject
    SharedPreferences preferences;

    @Bind(R.id.createSurveyBtn)
    Button createSurveyBtn;

    View view;
    SharedPrefManager pref;

    public static MySurveyFragment newInstance(Bundle bundle) {

        MySurveyFragment fragment = new MySurveyFragment();
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

        view = inflater.inflate(R.layout.my_survey, container, false);
        ButterKnife.bind(this, view);

        createSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String randomID = UUID.randomUUID().toString();
                rController.surveyLocalStorageS0(context, randomID, "local_progress");

                Intent intent = new Intent(getActivity(), CategoryParlimenActivity.class);
                intent.putExtra("LocalSurveyID",randomID);
                getActivity().startActivity(intent);

                /*initiateLoading(getActivity());

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserID(txtAuthID.getText().toString());
                loginRequest.setUserPassword(txtAuthPassword.getText().toString());
                presenter.onLoginRequest(loginRequest);*/

            }
        });

        return view;
    }


    /*@Subscribe
    public void onLoginReceive(LoginReceive loginReceive) {

        dismissLoading();

        if (loginReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {
                String status = loginReceive.getStatus();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("status", status);
                editor.putBoolean("auth_status", true);
                editor.putBoolean("just_login", true);
                editor.apply();

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


    }*/

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }*/

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}

