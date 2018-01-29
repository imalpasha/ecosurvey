package com.app.ecosurvey.ui.Activity.survey;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
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

    @Bind(R.id.block1)
    LinearLayout block1;

    @Bind(R.id.block2)
    LinearLayout block2;

    @Bind(R.id.block3)
    LinearLayout block3;

    @Bind(R.id.block4)
    LinearLayout block4;

    @Bind(R.id.block5)
    LinearLayout block5;

    @Bind(R.id.block6)
    LinearLayout block6;

    @Bind(R.id.txtSurveyIssue)
    EditText txtSurveyIssue;

    @Bind(R.id.txtIssueErr)
    TextView txtIssueErr;

    @Bind(R.id.btnHide)
    LinearLayout btnHide;

    private String randomID;
    private String status;

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

        final View view = inflater.inflate(R.layout.survey_issue, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();
            Log.e("SurveyCategory", survey.getSurveyCategory());
            Log.e("SurveyParliment", survey.getSurveyParliment());

        } finally {
            realm.close();
        }
        autoFill();
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
                    intent.putExtra("Status",status);
                    getActivity().startActivity(intent);
                }


            }
        });

        if (status != null) {
            if (status.equalsIgnoreCase("EDIT")) {

                block1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CategoryParlimenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID",randomID);
                        intent.putExtra("Status",status);
                        getActivity().startActivity(intent);
                    }
                });

                block3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyWishlistActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID",randomID);
                        intent.putExtra("Status",status);
                        getActivity().startActivity(intent);
                    }
                });

                block4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyPhotoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID",randomID);
                        intent.putExtra("Status",status);
                        getActivity().startActivity(intent);
                    }
                });

                block5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyVideoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID",randomID);
                        intent.putExtra("Status",status);
                        getActivity().startActivity(intent);
                    }
                });

                block6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyReviewActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID",randomID);
                        intent.putExtra("Status",status);
                        getActivity().startActivity(intent);
                    }
                });

            }
        }

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;


                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    Log.e("keyboard","Up");
                    btnHide.setVisibility(View.VISIBLE);
                }
                else {
                    // keyboard is closed
                    Log.e("keyboard","Down");
                    btnHide.setVisibility(View.GONE);

                }
            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity(),view);
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

                    txtSurveyIssue.setText(survey.getSurveyIssue());

                } finally {
                    realm.close();
                }
            }
        }

    }

    //need to move to base to standardize
    public Boolean manualValidation() {

        Boolean status;

        if (txtSurveyIssue.getText().toString().equalsIgnoreCase("")) {
            txtSurveyIssue.setFocusable(true);
            txtSurveyIssue.requestFocus();
            //txtIssueErr.setVisibility(View.VISIBLE);
            //txtSurveyIssue.setError("Please select parlimen");

            setError(getActivity(),"Validation Error.","Please insert your issue");

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

