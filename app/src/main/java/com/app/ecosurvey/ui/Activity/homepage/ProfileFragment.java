package com.app.ecosurvey.ui.Activity.homepage;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.login.LoginActivity;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;

    @Bind(R.id.profileLoading)
    LinearLayout profileLoading;

    @Bind(R.id.profileLayout)
    LinearLayout profileLayout;

    @Bind(R.id.btnRetry)
    LinearLayout btnRetry;

    @Bind(R.id.loadingSegment)
    LinearLayout loadingSegment;

    @Bind(R.id.btnToRetry)
    Button btnToRetry;

    @Bind(R.id.btnLogout)
    Button btnLogout;

    @Bind(R.id.txtName)
    TextView txtName;

    @Bind(R.id.txtPhoneNo)
    TextView txtPhoneNo;

    @Bind(R.id.txtEmail)
    TextView txtEmail;

    @Bind(R.id.txtParlimen)
    TextView txtParlimen;

    @Bind(R.id.txtPDM)
    TextView txtPDM;

    @Bind(R.id.txtState)
    TextView txtState;

    @Bind(R.id.txtDun)
    TextView txtDun;

    @Bind(R.id.txtRole)
    TextView txtRole;





    private View view;
    private SharedPrefManager pref;
    private String randomID;
    private String token;

    public static ProfileFragment newInstance(Bundle bundle) {

        ProfileFragment fragment = new ProfileFragment();
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

        view = inflater.inflate(R.layout.my_profile, container, false);
        ButterKnife.bind(this, view);

        token = preferences.getString("temp_token", "DEFAULT");
        profileLoading.setVisibility(View.VISIBLE);
        loadProfile();

        btnToRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProfile();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("just_login", false);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void loadProfile() {

        //load_user_info
        loadingSegment.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.GONE);

        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setToken(token);
        userInfoRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/user/" + 7777);
        presenter.onUserInfoRequest(userInfoRequest);

    }


    @Subscribe
    public void onUserInfoReceive(UserInfoReceive userInfoReceive) {

        if (userInfoReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                profileLoading.setVisibility(View.GONE);
                profileLayout.setVisibility(View.VISIBLE);

                txtName.setText(userInfoReceive.getData().getName());
                txtPhoneNo.setText(userInfoReceive.getData().getPhoneno());
                txtEmail.setText(userInfoReceive.getData().getEmail());

                if (userInfoReceive.getData().getParlimen() != null && userInfoReceive.getData().getParlimenCode() != null)
                    txtParlimen.setText(userInfoReceive.getData().getParlimen() + " (" + userInfoReceive.getData().getParlimenCode() + ")");

                if (userInfoReceive.getData().getDun() != null && userInfoReceive.getData().getDuncode() != null)
                    txtDun.setText(userInfoReceive.getData().getDun() + " (" + userInfoReceive.getData().getDuncode() + ")");

                if (userInfoReceive.getData().getPdm() != null && userInfoReceive.getData().getPdmcode() != null)
                    txtPDM.setText(userInfoReceive.getData().getParlimen() + " (" + userInfoReceive.getData().getParlimenCode() + ")");

                if (userInfoReceive.getData().getState() != null)
                    txtState.setText(userInfoReceive.getData().getState());

                if (userInfoReceive.getData().getRolename() != null)
                    txtRole.setText(userInfoReceive.getData().getRolename());


            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
                loadingSegment.setVisibility(View.GONE);
                btnRetry.setVisibility(View.VISIBLE);

            }

        } else {

            //display retry button

            //String error_msg = userInfoReceive.getMessage();
            //setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
            loadingSegment.setVisibility(View.GONE);
            btnRetry.setVisibility(View.VISIBLE);

        }


    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }*/

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

