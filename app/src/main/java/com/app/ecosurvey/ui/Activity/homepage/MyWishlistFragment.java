package com.app.ecosurvey.ui.Activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Activity.adapter.ChecklistAdapter;
import com.app.ecosurvey.ui.Model.Adapter.Object.CheckList;
import com.app.ecosurvey.ui.Model.Realm.Object.ChecklistCached;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.PostChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.Content;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostChecklistRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyWishlistFragment extends BaseFragment {

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

    @Bind(R.id.listview)
    ListView mListView;

    @Bind(R.id.no_list)
    LinearLayout no_list;

    @Bind(R.id.have_list)
    LinearLayout have_list;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.updateBtn)
    Button updateBtn;

    View view;
    String token;
    String userId;
    private ChecklistAdapter mAdapter;
    private List<CheckList> surveyLists;

    String[] parliment;
    String[] category;

    //private String randomID;
    private String icNumber;

    public static MyWishlistFragment newInstance(Bundle bundle) {

        MyWishlistFragment fragment = new MyWishlistFragment();
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

        view = inflater.inflate(R.layout.my_wishlist, container, false);
        ButterKnife.bind(this, view);

        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);

        token = preferences.getString("temp_token", "DEFAULT");
        userId = preferences.getString("user_id", "");

        getData();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshChecklist();

            }
        });

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright);

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChecklist();
            }
        });
        return view;
    }

    public void refreshChecklist(){

        ChecklistRequest checklistRequest = new ChecklistRequest();
        checklistRequest.setToken(token);
        checklistRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/checklist/submission/" + userId);
        presenter.onChecklistRequest(checklistRequest);
    }

    @Subscribe
    public void onChecklistReceive(ChecklistReceive checklistReceive) {

        swipeContainer.setRefreshing(false);

        if (checklistReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //save to realm
            //convert to gson
            Gson gson = new Gson();
            String checklist = gson.toJson(checklistReceive);

            rController.saveChecklist(context, checklist);
            getData();

        } else {

            String error_msg = checklistReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }

    public void getData(){
        //call from Realm

        Realm realm = rController.getRealmInstanceContext(context);
        Log.e("TEST", "1");
        try {

            ChecklistCached checklist = realm.where(ChecklistCached.class).findFirst();
            Gson gson = new Gson();
            ChecklistReceive checklistReceive = gson.fromJson(checklist.getCheckListString(), ChecklistReceive.class);
            Log.e("TEST", "2");
            if (checklistReceive.getData() != null) {

                if (checklistReceive.getData().size() != 0) {
                    Log.e("TEST", "3");
                    //Collections.reverse(notificationInfo);
                    mAdapter = new ChecklistAdapter(getActivity(), this, checklistReceive);
                    mListView.setAdapter(mAdapter);
                /*mAdapter.setMode(Attributes.Mode.Single);*/

                } else {
                    Log.e("TEST", "4");
                    have_list.setVisibility(View.GONE);
                    no_list.setVisibility(View.VISIBLE);
                }

            } else {
                Log.e("TEST", "5");
                have_list.setVisibility(View.GONE);
                no_list.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {

            Log.e("UnableToLoad", "Y");
            have_list.setVisibility(View.GONE);
            no_list.setVisibility(View.VISIBLE);
        } finally {

            realm.close();
        }





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

    public String showList(){
        List x = checklist();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String stringContent = gson.toJson(x);
        Log.e("LIST LIST", stringContent);

        return stringContent;
    }

    public void insertList(int position, String i, String t, String c){

        Log.e("position", String.valueOf(position));
        surveyLists = new ArrayList<CheckList>();

        for (int p = 0; p <= position; p++) {

        CheckList info = new CheckList();
        info.setCategoryid(i);
        info.setIssue(t);
        info.setWishlist(c);
        surveyLists.add(info);

        }
    }

    public void submitChecklist(){
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Eco Survey")
                .setContentText("Confirm submit this checklist?")
                .setCancelText("No")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        initiateLoading(getActivity());

                        Realm realm2 = rController.getRealmInstanceContext(context);

                        try {

                            RealmResults<UserInfoCached> infoCached = realm2.where(UserInfoCached.class).findAll();
                            //LocalSurvey survey = realm2.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                            if (infoCached.size() > 0) {

                                Gson gson = new Gson();
                                UserInfoReceive obj = gson.fromJson(infoCached.get(0).getUserInfoString(), UserInfoReceive.class);
                                icNumber = obj.getData().getIcNumber();

                                PostChecklistRequest postChecklistRequest = new PostChecklistRequest();
                                postChecklistRequest.setIcNumber(icNumber);

                                //parliment = survey.getSurveyParliment().split("/");
                                //category = survey.getSurveyCategory().split("/");

                                String content = showList();

                                //postChecklistRequest.setLocationCode(parliment[1]);
                                postChecklistRequest.setLocationCode("01001");
                                //postChecklistRequest.setLocationName(parliment[0]);
                                postChecklistRequest.setLocationName("PADANG BESAR");
                                postChecklistRequest.setLocationType("PAR");
                                postChecklistRequest.setContent(content);
                                postChecklistRequest.setToken(preferences.getString("temp_token", ""));
                                presenter.onPostChecklist(postChecklistRequest);
                            }


                        } finally {
                            realm2.close();
                        }



                        sDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismiss();

                    }
                })
                .show();
    }

    @Subscribe
    public void onPostChecklistReceive(PostChecklistReceive postChecklistReceive) {

        dismissLoading();

        if (postChecklistReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {
                Log.e("POST CHECKLIST", "Success");

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = postChecklistReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    public List checklist(){
        return surveyLists;
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

