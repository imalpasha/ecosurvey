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
import com.app.ecosurvey.ui.Model.Realm.Object.CachedCategory;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.CategoryReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.DropDownItem;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class CategoryParlimenFragment extends BaseFragment {


    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    @Bind(R.id.categoryBtnNext)
    Button categoryBtnNext;

    @Bind(R.id.block1)
    LinearLayout block1;

    @Bind(R.id.txtParlimen)
    TextView txtParlimen;

    @Bind(R.id.txtKategori)
    TextView txtKategori;

    private String randomID;

    private ArrayList<DropDownItem> parlimenList = new ArrayList<>();
    private ArrayList<DropDownItem> kategoriList = new ArrayList<>();

    private int fragmentContainerId;

    public static CategoryParlimenFragment newInstance(Bundle bundle) {

        CategoryParlimenFragment fragment = new CategoryParlimenFragment();
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

        View view = inflater.inflate(R.layout.category_parlimen, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");

        setData();
        setupBlock(getActivity(), block1);

        categoryBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (manualValidation()) {

                    String category = txtKategori.getText().toString();
                    String parliment = txtParlimen.getText().toString();


                    rController.surveyLocalStorageS1(context, randomID, category, parliment, "local_progress");

                    Intent intent = new Intent(getActivity(), SurveyIssueActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    intent.putExtra("LocalSurveyID", randomID);
                    getActivity().startActivity(intent);
                }


                /*initiateLoading(getActivity());

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserID(txtAuthID.getText().toString());
                loginRequest.setUserPassword(txtAuthPassword.getText().toString());
                presenter.onLoginRequest(loginRequest);*/

            }
        });

        txtParlimen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupSelection(parlimenList, getActivity(), txtParlimen, true);

            }
        });

        txtKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupSelection(kategoriList, getActivity(), txtKategori, true);

            }
        });


        return view;
    }

    public Boolean manualValidation() {

        Boolean status;

        if (txtParlimen.getText().toString().equalsIgnoreCase("")) {
            setShake(txtParlimen);
            txtParlimen.setFocusable(true);
            txtParlimen.requestFocus();
            txtParlimen.setError("Please select parlimen");
            status = false;
        } else if (txtKategori.getText().toString().equalsIgnoreCase("")) {
            setShake(txtKategori);
            txtKategori.setFocusable(true);
            txtKategori.requestFocus();
            txtKategori.setError("Please select kategori");
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void setData() {

        ArrayList<String> category = new ArrayList<String>();
        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {

            RealmResults<CachedCategory> survey = realm.where(CachedCategory.class).findAll();
            if (survey.size() > 0) {

                Gson gson = new Gson();
                CategoryReceive obj = gson.fromJson(survey.get(0).getCategoryList(), CategoryReceive.class);

                Log.e("getSize", Integer.toString(obj.getData().getItems().size()));
                for (int c = 0; c < obj.getData().getItems().size(); c++) {
                    category.add(obj.getData().getItems().get(c).getTitle() + "/" + obj.getData().getItems().get(c).getId());
                }

            }


        } finally {
            realm.close();
        }

        String[] parlimenDummy = new String[]{"Parlimen A", "Parlimen B", "Parlimen C", "Parlimen D"};

         /*Display Airport*/
        for (int i = 0; i < parlimenDummy.length; i++) {
            DropDownItem itemFlight = new DropDownItem();
            itemFlight.setText(parlimenDummy[i]);
            itemFlight.setCode(Integer.toString(i));
            parlimenList.add(itemFlight);

        }

        for (int i = 0; i < category.size(); i++) {
            DropDownItem itemFlight = new DropDownItem();

            String[] parts = category.get(i).split("/");

            itemFlight.setText(parts[0]);
            itemFlight.setCode(parts[1]);
            kategoriList.add(itemFlight);

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

