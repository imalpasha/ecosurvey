package com.app.ecosurvey.ui.Activity.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SurveyWishlistFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    @Bind(R.id.wishlistBtnNext)
    Button wishlistBtnNext;

    @Bind(R.id.block3)
    LinearLayout block3;

    @Bind(R.id.txtWishlistIssue)
    TextView txtWishlistIssue;

    private String randomID;
    private String status;

    public static SurveyWishlistFragment newInstance(Bundle bundle) {

        SurveyWishlistFragment fragment = new SurveyWishlistFragment();
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

        View view = inflater.inflate(R.layout.survey_wishlist, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");

        setupBlock(getActivity(), block3);

        autoFill();

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();
            Log.e("SurveyCategory", survey.getSurveyCategory());
            Log.e("SurveyParliment", survey.getSurveyParliment());
            Log.e("SurveyIssue", survey.getSurveyIssue());

        } finally {
            realm.close();
        }

        wishlistBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wishlist = txtWishlistIssue.getText().toString();


                rController.surveyLocalStorageS3(context, randomID, wishlist);

                Intent intent = new Intent(getActivity(), SurveyPhotoActivity.class);
                intent.putExtra("LocalSurveyID",randomID);
                intent.putExtra("Status",status);
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

    public void autoFill(){

        if (status != null){
            if (status.equalsIgnoreCase("EDIT")){

                //try fetch realm data.
                Realm realm = rController.getRealmInstanceContext(context);
                try {
                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                    if(!survey.getSurveyWishlist().equalsIgnoreCase("") || survey.getSurveyWishlist() != null){
                        txtWishlistIssue.setText(survey.getSurveyWishlist());
                    }

                } finally {
                    realm.close();
                }
            }
        }

    }

    @Subscribe
    public void onLoginReceive(LoginReceive loginReceive) {

        dismissLoading();

        if (loginReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

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

