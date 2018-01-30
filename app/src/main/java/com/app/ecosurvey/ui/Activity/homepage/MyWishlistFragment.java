package com.app.ecosurvey.ui.Activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Activity.adapter.ChecklistAdapter;
import com.app.ecosurvey.ui.Model.Realm.Object.ChecklistCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;
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

    View view;
    String token;
    String userId;

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
        //try {

            ChecklistCached checklist = realm.where(ChecklistCached.class).findFirst();
            Gson gson = new Gson();
            ChecklistReceive checklistReceive = gson.fromJson(checklist.getCheckListString(), ChecklistReceive.class);
            Log.e("TEST", "2");
            if (checklistReceive.getData() != null) {

                if (checklistReceive.getData().size() != 0) {
                    Log.e("TEST", "3");
                    //Collections.reverse(notificationInfo);
                    ChecklistAdapter mAdapter = new ChecklistAdapter(getActivity(), this, checklistReceive);
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

        /*} catch (Exception e) {

            Log.e("UnableToLoad", "Y");
        } finally {

            realm.close();
        }*/





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

