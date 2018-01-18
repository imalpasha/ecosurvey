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

public class SurveyIssueFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    @Bind(R.id.issueBtnNext)
    Button issueBtnNext;

    @Bind(R.id.block2)
    LinearLayout block2;

    @Bind(R.id.txtSurveyIssue)
    TextView txtSurveyIssue;

    @Bind(R.id.txtIssueErr)
    TextView txtIssueErr;

    private String randomID;

    public static SurveyIssueFragment newInstance(Bundle bundle) {

        SurveyIssueFragment fragment = new SurveyIssueFragment();
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

        View view = inflater.inflate(R.layout.survey_issue, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();
            Log.e("SurveyCategory", survey.getSurveyCategory());
            Log.e("SurveyParliment", survey.getSurveyParliment());

        } finally {
            realm.close();
        }

        setData();
        setupBlock(getActivity(), block2);

        issueBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mandatory
                if (manualValidation()) {

                    String surveyIssue = txtSurveyIssue.getText().toString();
                    rController.surveyLocalStorageS2(context, randomID, surveyIssue);

                    Intent intent = new Intent(getActivity(), SurveyWishlistActivity.class);
                    intent.putExtra("LocalSurveyID",randomID);
                    getActivity().startActivity(intent);
                }


            }
        });

        return view;
    }

    //need to move to base to standardize
    public Boolean manualValidation() {

        Boolean status;

        if (txtSurveyIssue.getText().toString().equalsIgnoreCase("")) {
            txtSurveyIssue.setFocusable(true);
            txtSurveyIssue.requestFocus();
            txtIssueErr.setVisibility(View.VISIBLE);
            //txtSurveyIssue.setError("Please select parlimen");
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void setData() {

        //txtSurveyIssue.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //txtSurveyIssue.setRawInputType(InputType.TYPE_CLASS_TEXT);

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

