package com.app.ecosurvey.ui.Activity.homepage;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.app.ecosurvey.ui.Model.Realm.Object.CachedCategory;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Receive.InitChecklistReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

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

    @Inject
    Context context;

    @Bind(R.id.logout)
    LinearLayout logout;

    @Bind(R.id.addParliment)
    LinearLayout addParliment;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.my_profile, container, false);
        ButterKnife.bind(this, view);

        token = preferences.getString("temp_token", "DEFAULT");
        profileLayout.setVisibility(View.VISIBLE);
        profileLoading.setVisibility(View.GONE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("just_login", false);
                editor.apply();

                rController.clearLocalSurvey(context);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });
        loadData();

        /*btnLogout.setVisibility(View.GONE);
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
        });*/

        return view;
    }


    public void loadData() {

        Realm realm = rController.getRealmInstanceContext(context);
        try {

            UserInfoCached survey = realm.where(UserInfoCached.class).findFirst();
            Gson gson = new Gson();
            UserInfoReceive userInfoReceive = gson.fromJson(survey.getUserInfoString(), UserInfoReceive.class);

            txtName.setText(userInfoReceive.getData().getName());
            txtPhoneNo.setText(userInfoReceive.getData().getPhoneNo());
            Log.e("phone", "phone" + userInfoReceive.getData().getPhoneNo());
            txtEmail.setText(userInfoReceive.getData().getEmail());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            for (int x = 0; x < userInfoReceive.getData().getLocations().size(); x++) {

                View childView = inflater.inflate(R.layout.txt_parliment, null, false);
                TextView txtPar = (TextView) childView.findViewById(R.id.txtPar);
                txtPar.setText(userInfoReceive.getData().getLocations().get(x).getParlimen() + " (" + userInfoReceive.getData().getLocations().get(x).getParlimenCode() + ")");

                addParliment.addView(childView);
            }



            /*
            if (userInfoReceive.getData().getDun() != null && userInfoReceive.getData().getDuncode() != null)
                txtDun.setText(userInfoReceive.getData().getDun() + " (" + userInfoReceive.getData().getDuncode() + ")");

            if (userInfoReceive.getData().getPdm() != null && userInfoReceive.getData().getPdmcode() != null)
                txtPDM.setText(userInfoReceive.getData().getParlimen() + " (" + userInfoReceive.getData().getParlimenCode() + ")");

            if (userInfoReceive.getData().getState() != null)
                txtState.setText(userInfoReceive.getData().getState());*/

            if (userInfoReceive.getData().getRole() != null)
                txtRole.setText(userInfoReceive.getData().getRole());

        } catch (Exception e) {

            Log.e("UnableToLoad", "Y");
        } finally {

            realm.close();
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

