package com.app.ecosurvey.ui.Activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.api.ApiEndpoint;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.adapter.ImageListAdapter;
import com.app.ecosurvey.ui.Activity.adapter.SurveyListAdapter;
import com.app.ecosurvey.ui.Activity.survey.SurveyPhotoFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Model.Adapter.Object.SurveyList;
import com.app.ecosurvey.ui.Model.Realm.Object.CachedResult;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ListSurveyReceive;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.ListSurveyRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.survey.CategoryParlimenActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MySurveyFragment extends BaseFragment {

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

    @Bind(R.id.createSurveyBtn)
    Button createSurveyBtn;

    @Bind(R.id.no_list)
    LinearLayout no_list;

    @Bind(R.id.have_list)
    LinearLayout have_list;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    View view;
    SharedPrefManager pref;

    SurveyListAdapter mAdapter;

    public static MySurveyFragment newInstance(Bundle bundle) {

        MySurveyFragment fragment = new MySurveyFragment();
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

        view = inflater.inflate(R.layout.my_survey, container, false);
        ButterKnife.bind(this, view);

        getData();

        Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        final String formattedDate = df.format(calendar.getTime());

        createSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String randomID = UUID.randomUUID().toString();
                rController.surveyLocalStorageS0(context, randomID, "local_progress", formattedDate);

                Intent intent = new Intent(getActivity(), CategoryParlimenActivity.class);
                intent.putExtra("LocalSurveyID", randomID);
                intent.putExtra("Status", "CREATE");
                getActivity().startActivity(intent);

                /*initiateLoading(getActivity());

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserID(txtAuthID.getText().toString());
                loginRequest.setUserPassword(txtAuthPassword.getText().toString());
                presenter.onLoginRequest(loginRequest);*/

            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                reloadList();
                swipeContainer.setRefreshing(true);

            }
        });

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright);

        return view;
    }

    public void confirmDelete(final Integer pos, final String id) {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Eco Survey.")
                .setContentText("Remove this from survey list?")
                .setCancelText("No")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        //mAdapter.confirmDelete(pos);
                        getData();
                        //mAdapter.notifyItemRemoved(pos);
                        //mAdapter.notifyItemRangeChanged(position, obj.size());
                        //mAdapter.notifyDataSetChanged();

                        //remove from realm
                        Realm realm = rController.getRealmInstanceContext(context);
                        try {
                            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", id).findFirst();

                            try {
                                realm.beginTransaction();
                                survey.removeFromRealm();
                                ;
                                realm.commitTransaction();
                                realm.close();
                            } catch (Exception e) {
                                Log.e("clearCached", e.getMessage());
                            }

                        } finally {
                            getData();
                            realm.close();
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

    public void editData(String randomID) {
        Intent intent = new Intent(getActivity(), CategoryParlimenActivity.class);
        intent.putExtra("LocalSurveyID", randomID);
        intent.putExtra("Status", "EDIT");
        getActivity().startActivity(intent);
    }

    public void reloadList(){

        String userId = preferences.getString("user_id", "");
        String token = preferences.getString("temp_token", "");

        ListSurveyRequest listSurveyRequest = new ListSurveyRequest();
        listSurveyRequest.setToken(token);
        listSurveyRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/surveys/" + userId);
        presenter.onListSurveyRequest(listSurveyRequest);

    }

    @Subscribe
    public void onListSurveyReceive(ListSurveyReceive listSurveyReceive) {

        swipeContainer.setRefreshing(false);
        if (listSurveyReceive.getApiStatus().equalsIgnoreCase("Y")) {

            //update_realm_with_local
            try {
                rController.updateLocalRealm(getActivity(), listSurveyReceive);
            } finally {
                getData();
            }

        } else {

            String error_msg = listSurveyReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }

    public void getData() {
        //call from Realm
        Realm realm = rController.getRealmInstanceContext(context);
        final RealmResults<LocalSurvey> result2 = realm.where(LocalSurvey.class).equalTo("surveyLocalProgress", "Completed").findAll();

        if (result2.size() != 0) {
            Log.e("totalsurvey", Integer.toString(result2.size()));

            //convert
            List<SurveyList> surveyLists = new ArrayList<SurveyList>();

            for (int position = result2.size() - 1; position >= 0; position--) {

                SurveyList info = new SurveyList();
                info.setLocalSurveyID(result2.get(position).getLocalSurveyID());
                info.setSurveyCategory(result2.get(position).getSurveyCategory());
                info.setSurveyParliment(result2.get(position).getSurveyParliment());
                info.setSurveyIssue(result2.get(position).getSurveyIssue());
                info.setSurveyWishlist(result2.get(position).getSurveyWishlist());
                info.setImageString(result2.get(position).getImageString());
                info.setSurveyStatus(result2.get(position).getSurveyStatus());
                info.setStatusCreated(result2.get(position).getStatusCreated());
                info.setStatusUpdated(result2.get(position).getStatusUpdated());
                surveyLists.add(info);

            }

            initiateImageAdapter(surveyLists);
        } else {
            have_list.setVisibility(View.GONE);
            no_list.setVisibility(View.VISIBLE);
        }
    }


    public void initiateImageAdapter(List<SurveyList> array) {

        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mAdapter = new SurveyListAdapter(getActivity(), this, array);

        myRecyclerView.setAdapter(mAdapter);
        myRecyclerView.setLayoutManager(MyLayoutManager);

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

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}

