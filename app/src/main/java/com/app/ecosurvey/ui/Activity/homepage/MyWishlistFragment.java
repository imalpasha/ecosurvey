package com.app.ecosurvey.ui.Activity.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.app.ecosurvey.ui.Model.Adapter.Object.ChecklistMerge;
import com.app.ecosurvey.ui.Model.Adapter.Object.ChildList;
import com.app.ecosurvey.ui.Model.Adapter.Object.MergeList;
import com.app.ecosurvey.ui.Model.Realm.Object.ChecklistCached;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.PostChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Receive.InitChecklistReceive;
import com.app.ecosurvey.ui.Model.Request.InitChecklistRequest;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.view.View.GONE;

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

    //@Bind(R.id.swipeContainer)
    //SwipeRefreshLayout swipeContainer;

    @Bind(R.id.updateBtn)
    Button updateBtn;

    @Bind(R.id.checklistload)
    LinearLayout checklistload;

    View view;
    String token;
    String userId;

    private ChecklistAdapter mAdapter;
    private List<CheckList> surveyLists;
    private String parliment, parlimentCode;
    private String checklistId, checkListLocation, checkListLocationName;
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

        //getData();
        getCheckList();


        /*mListView.setOnTouchListener(new View.OnTouchListener() {
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
        });*/

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitChecklist();
            }
        });
        return view;
    }

    public void getCheckList() {

        InitChecklistRequest initChecklistRequest = new InitChecklistRequest();
        initChecklistRequest.setToken(token);
        initChecklistRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/checklist");
        presenter.onInitChecklistRequest(initChecklistRequest);

    }

    public void refreshChecklist() {

        ChecklistRequest checklistRequest = new ChecklistRequest();
        checklistRequest.setToken(token);
        checklistRequest.setUrl(ApiEndpoint.getUrl() + "/api/v1/checklist/submission/" + preferences.getString("user_id", ""));
        presenter.onChecklistRequest(checklistRequest);

    }


    @Subscribe
    public void onInitChecklistReceive(InitChecklistReceive initChecklistReceive) {

        if (initChecklistReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                Gson gson = new Gson();
                String checklist = gson.toJson(initChecklistReceive);

                rController.saveInitChecklist(context, checklist);
                //getData();

                checklistload.setVisibility(GONE);
                refreshChecklist();

            } catch (Exception e) {

            }
        }
    }


    @Subscribe
    public void onChecklistReceive(ChecklistReceive checklistReceive) {

        //swipeContainer.setRefreshing(false);
        dismissLoading();
        if (checklistReceive.getApiStatus().equalsIgnoreCase("Y")) {

            getData(checklistReceive);

        } else {

            String error_msg = checklistReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);
        }
    }

    public void getData(ChecklistReceive checklistReceive) {
        //call from Realm

        Realm realm = rController.getRealmInstanceContext(context);

        try {

            ChecklistCached checklist = realm.where(ChecklistCached.class).findFirst();
            Gson gson = new Gson();
            InitChecklistReceive obj = gson.fromJson(checklist.getCheckListString(), InitChecklistReceive.class);

            //create another set of object to manage the checklist
            ArrayList<MergeList> listsMerge = new ArrayList<MergeList>();

            Log.e("sub", Integer.toString(obj.getData().size()));

            for (int sub = 0; sub < obj.getData().size(); sub++) {
                if (obj.getData().get(sub).getParent_id().equalsIgnoreCase("0")) {
                    MergeList mergeList = new MergeList();
                    mergeList.setHeader(obj.getData().get(sub).getText());
                    mergeList.setId(obj.getData().get(sub).getId());

                    for (int sub2 = 0; sub2 < obj.getData().size(); sub2++) {

                        if (obj.getData().get(sub2).getParent_id().equalsIgnoreCase(obj.getData().get(sub).getId())) {

                            ChildList childList2 = new ChildList();
                            childList2.setCheck("no");
                            childList2.setTxtComment("no");
                            childList2.setTxtID(obj.getData().get(sub2).getId());
                            childList2.setTxtName(obj.getData().get(sub2).getText());

                            mergeList.getChildLists().add(childList2);
                        }
                    }
                    listsMerge.add(mergeList);
                }

            }

            //set the selected
            for (int sub3 = 0; sub3 < checklistReceive.getData().getContent().size(); sub3++) {
                for (int header = 0; header < listsMerge.size(); header++) {
                    for (int child = 0; child < listsMerge.get(header).getChildLists().size(); child++) {
                        if (listsMerge.get(header).getChildLists().get(child).getTxtID().equalsIgnoreCase(checklistReceive.getData().getContent().get(sub3).getItemid())) {

                            listsMerge.get(header).getChildLists().get(child).setTxtComment(checklistReceive.getData().getContent().get(sub3).getComment());
                            listsMerge.get(header).getChildLists().get(child).setCheck(checklistReceive.getData().getContent().get(sub3).getCheck());

                        }
                    }
                }
            }

            checklistId = checklistReceive.getData().getId();
            checkListLocation = checklistReceive.getData().getLocationCode();
            checkListLocationName = checklistReceive.getData().getLocationName();

            mAdapter = new ChecklistAdapter(getActivity(), this, listsMerge);
            mListView.setAdapter(mAdapter);

        } catch (Exception e) {

            Log.e("UnableToLoad", e.getMessage());
            have_list.setVisibility(GONE);
            no_list.setVisibility(View.VISIBLE);
        } finally {

            //realm.close();
        }

    }

    public String showList() {
        List x = checklist();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String stringContent = gson.toJson(x);
        Log.e("LIST LIST", stringContent);

        return stringContent;
    }

    public void insertList(int position, String i, String t, String c) {

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

    public void submitChecklist() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Eco Survey")
                .setContentText("Confirm submit this checklist?")
                .setCancelText("No")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        initiateLoadingMsg(getActivity(), "Submitting checklist...");

                        Realm realm2 = rController.getRealmInstanceContext(context);

                        try {

                            RealmResults<UserInfoCached> infoCached = realm2.where(UserInfoCached.class).findAll();
                            //LocalSurvey survey = realm2.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                            if (infoCached.size() > 0) {

                                Gson gson = new Gson();
                                UserInfoReceive obj = gson.fromJson(infoCached.get(0).getUserInfoString(), UserInfoReceive.class);
                                icNumber = obj.getData().getIcNumber();

                                HashMap<String, RequestBody> map = new HashMap<>();
                                map.put("IcNumber", toRequestBody(icNumber));
                                map.put("id", toRequestBody(checklistId));
                                map.put("locationCode", toRequestBody(checkListLocation));
                                map.put("locationName", toRequestBody(checkListLocationName));
                                map.put("locationType", toRequestBody("PAR"));

                                ArrayList<MergeList> listsMerge = mAdapter.checklistObj();
                                List<Content> listMultipart = new ArrayList<>();

                                for (int total = 0; total < listsMerge.size(); total++) {
                                    for (int toSend = 0; toSend < listsMerge.get(total).getChildLists().size(); toSend++) {

                                        Content content = new Content();
                                        content.setItemid(listsMerge.get(total).getChildLists().get(toSend).getTxtID());
                                        content.setComment(listsMerge.get(total).getChildLists().get(toSend).getTxtComment());
                                        content.setCheck(listsMerge.get(total).getChildLists().get(toSend).getCheck());

                                        listMultipart.add(content);

                                    }
                                }

                                PostChecklistRequest postChecklistRequest = new PostChecklistRequest();
                                postChecklistRequest.setMap(map);
                                postChecklistRequest.setParts2(listMultipart);

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

    public RequestBody toRequestBody(String val) {

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), val);
        return body;
    }

    @Subscribe
    public void onPostChecklistReceive(PostChecklistReceive obj) {

        dismissLoading();

        if (obj.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Success.")
                        .setContentText("Checklist successfully saved.")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismiss();
                                initiateLoadingMsg(getActivity(), "Updating checklist...");
                                refreshChecklist();

                            }
                        })
                        .show();


            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = obj.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    public List checklist() {
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

