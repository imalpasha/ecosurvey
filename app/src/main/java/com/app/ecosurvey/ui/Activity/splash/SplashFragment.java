package com.app.ecosurvey.ui.Activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.login.LoginActivity;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.TokenReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.TokenRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;


    View view;
    SharedPrefManager pref;

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

        //get_token

        //initiateLoading(getActivity());
        //TokenRequest tokenRequest = new TokenRequest();
        //presenter.onTokenRequest(tokenRequest);

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("temp_token", "12345");
        editor.apply();

        return view;
    }


    @Subscribe
    public void onTokenReceive(TokenReceive tokenReceive) {

        dismissLoading();

        if (tokenReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                String token = tokenReceive.getToken();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("temp_token", token);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);

                //load category_daily
                //get_categories
                //CategoryRequest categoryRequest = new CategoryRequest();
                //presenter.onCategoryRequest(categoryRequest);

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = tokenReceive.getMessage();
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

