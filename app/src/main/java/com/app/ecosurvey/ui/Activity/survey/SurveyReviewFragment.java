package com.app.ecosurvey.ui.Activity.survey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.homepage.TabActivity;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Realm.Object.UserInfoCached;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.PostSurveyReceive;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.UserInfoReceive;
import com.app.ecosurvey.ui.Model.Request.SurveyPhotoRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.Content;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostSurveyRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String formattedDate;
    private String icNumber;

    String[] parliment;
    String[] category;


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

        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");

        Log.e("localID", bundle.getString("LocalSurveyID"));
        setupBlock(getActivity(), block6);

        setData();

        Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        formattedDate = df.format(calendar.getTime());

        surveySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Eco Survey")
                        .setContentText("Confirm save this survey?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                rController.surveyLocalStorageS5(context, randomID, formattedDate, "Completed", "", null);

                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                getActivity().startActivity(intent);

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
        });

        surveySubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateStatusData();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Eco Survey")
                        .setContentText("Confirm submit this survey?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                initiateLoading(getActivity());

                                Realm realm2 = rController.getRealmInstanceContext(context);

                                try {

                                    RealmResults<UserInfoCached> infoCached = realm2.where(UserInfoCached.class).findAll();
                                    LocalSurvey survey = realm2.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                                    if (infoCached.size() > 0) {

                                        Gson gson = new Gson();
                                        UserInfoReceive obj = gson.fromJson(infoCached.get(0).getUserInfoString(), UserInfoReceive.class);
                                        icNumber = obj.getData().getIcNumber();

                                        PostSurveyRequest postSurveyRequest = new PostSurveyRequest();
                                        postSurveyRequest.setIcNumber(icNumber);

                                        parliment = survey.getSurveyParliment().split("/");
                                        category = survey.getSurveyCategory().split("/");

                                        Content content = new Content();
                                        content.setCategoryid(category[1]);
                                        content.setIssue(survey.getSurveyIssue());
                                        content.setWishlist(survey.getSurveyWishlist());

                                        gson = new GsonBuilder().disableHtmlEscaping().create();
                                        String stringContent = gson.toJson(content);

                                        //HashMap<String, Object> dicMap = new HashMap<String, Object>();
                                        //dicMap.put("content[]", content);

                                        //contents.add(content);

                                        postSurveyRequest.setLocationCode(parliment[1]);
                                        postSurveyRequest.setLocationName(parliment[0]);
                                        postSurveyRequest.setLocationType("?");
                                        postSurveyRequest.setContent(stringContent);
                                        postSurveyRequest.setToken(preferences.getString("temp_token", ""));

                                        presenter.onPostSurvey(postSurveyRequest);
                                    }


                                } finally {
                                    realm2.close();
                                }





                                /*rController.surveyLocalStorageS5(context, randomID, formattedDate);
                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                getActivity().startActivity(intent);*/

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
        });

        if (status != null) {
            if (status.equalsIgnoreCase("EDIT")) {
                block1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CategoryParlimenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
                        getActivity().startActivity(intent);
                    }
                });

                block2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyIssueActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
                        getActivity().startActivity(intent);
                    }
                });

                block3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyWishlistActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
                        getActivity().startActivity(intent);
                    }
                });

                block4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyPhotoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
                        getActivity().startActivity(intent);
                    }
                });

                block5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyVideoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
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


            String[] parliment = survey.getSurveyParliment().split("/");
            String[] category = survey.getSurveyCategory().split("/");

            txtParlimen.setText(parliment[0]);
            txtKategori.setText(category[0]);
            txtSurveyIssue.setText(survey.getSurveyIssue());

            if (survey.getSurveyWishlist().equalsIgnoreCase("")) {
                wishlistTitle.setVisibility(View.GONE);
                wishlistBlock.setVisibility(View.GONE);
                Log.e("nowishlist", "a" + survey.getSurveyWishlist());
            } else {
                txtSurveyWishlist.setText(survey.getSurveyWishlist());
                Log.e("nowishlist", "ab" + survey.getSurveyWishlist());


            }

            //Log.e("SurveyWishlist", survey.getSurveyWishlist());

            //for (int x = 0; x < survey.getImagePath().size(); x++) {
            //    Log.e("SurveyImagePath", survey.getImagePath().get(x).toString());
            //}

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
    public void onPostSurveyReceive(PostSurveyReceive postSurveyReceive) {

        dismissLoading();

        if (postSurveyReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                //save info to realm with proper id
                //setSuccess(getActivity(), "Success.", "Survey successfully saved.");
                rController.surveyLocalStorageS5(context, randomID, formattedDate, "Completed", "API-STATUS", postSurveyReceive.getId());

                /*new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Success.")
                        .setContentText("Survey successfully saved.")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                getActivity().startActivity(intent);

                                sDialog.dismiss();
                            }
                        })
                        .show();*/

                //get saved photo
                List<MultipartBody.Part> listMultipart = new ArrayList<>();
                Realm realm = rController.getRealmInstanceContext(context);
                try {
                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();
                    String imageList = survey.getImagePath();

                    String[] parts = imageList.split("___");
                    for (int b = 0; b < parts.length; b++) {
                        if (parts[b] != null) {
                            Uri myUri = Uri.parse(parts[b]);
                            listMultipart.add(prepareFilePart("photo", myUri));
                        }
                    }


                } catch (Exception e) {
                    Log.e("ERROR_MSG", e.getMessage());
                } finally {
                    realm.close();
                }

                //submit photo.
                SurveyPhotoRequest surveyPhotoRequest = new SurveyPhotoRequest();
                surveyPhotoRequest.setIcnumber(icNumber);
                surveyPhotoRequest.setLocationCode(parliment[1]);
                surveyPhotoRequest.setLocationName(parliment[0]);
                surveyPhotoRequest.setLocationType("PAR");
                surveyPhotoRequest.setParts(listMultipart);


                //presenter.onSurveyPhotoRequest(surveyPhotoRequest);

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = postSurveyReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(fileUri)), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
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

