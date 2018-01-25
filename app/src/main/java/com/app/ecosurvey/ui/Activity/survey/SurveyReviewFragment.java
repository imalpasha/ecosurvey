package com.app.ecosurvey.ui.Activity.survey;

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
import android.widget.Toast;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.homepage.TabActivity;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SurveyReviewFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    SharedPreferences preferences;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    Context context;

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

    @Bind(R.id.txtParlimen)
    TextView txtParlimen;

    @Bind(R.id.txtKategori)
    TextView txtKategori;

    @Bind(R.id.txtSurveyIssue)
    TextView txtSurveyIssue;

    @Bind(R.id.txtSurveyWishlist)
    TextView txtSurveyWishlist;

    @Bind(R.id.wishlistTitle)
    TextView wishlistTitle;

    @Bind(R.id.wishlistBlock)
    LinearLayout wishlistBlock;

    @Bind(R.id.surveySaveBtn)
    Button surveySaveBtn;

    @Bind(R.id.surveySubmitBtn)
    Button surveySubmitBtn;

    private String randomID;
    private String status;

    public static SurveyReviewFragment newInstance(Bundle bundle) {

        SurveyReviewFragment fragment = new SurveyReviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.component(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.survey_review, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");

        Log.e("localID", bundle.getString("LocalSurveyID"));
        setupBlock(getActivity(), block6);

        setData();

        Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        final String formattedDate = df.format(calendar.getTime());

        surveySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rController.surveyLocalStorageS5(context, randomID, formattedDate);

                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                getActivity().startActivity(intent);

            }
        });

        surveySubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusData();
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

                block2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyIssueActivity.class);
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
            }
        }



        return view;
    }


    public void setData() {

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

            txtParlimen.setText(survey.getSurveyParliment());
            txtKategori.setText(survey.getSurveyCategory());
            txtSurveyIssue.setText(survey.getSurveyIssue());

            if (survey.getSurveyWishlist() == "") {
                wishlistTitle.setVisibility(View.GONE);
                wishlistBlock.setVisibility(View.GONE);
            } else {
                txtSurveyWishlist.setText(survey.getSurveyWishlist());
            }

            Log.e("SurveyWishlist", survey.getSurveyWishlist());

            for (int x = 0; x < survey.getImagePath().size(); x++) {
                Log.e("SurveyImagePath", survey.getImagePath().get(x).toString());
            }

        } finally {
            realm.close();
        }

    }

    public void updateStatusData() {

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

            String status = survey.getSurveyLocalProgress();
            Log.e("SurveyLocalProgress", status);

        } finally {
            realm.close();
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

