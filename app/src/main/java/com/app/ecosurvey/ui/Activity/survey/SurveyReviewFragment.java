package com.app.ecosurvey.ui.Activity.survey;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import com.app.ecosurvey.ui.Model.Receive.SurveyPhotoReceive;
import com.app.ecosurvey.ui.Model.Request.SurveyPhotoContentRequest;
import com.app.ecosurvey.ui.Model.Request.SurveyPhotoRequest;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.Content;
import com.app.ecosurvey.ui.Model.Request.ecosurvey.PostSurveyRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikepenz.iconics.utils.Utils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import static com.app.ecosurvey.ui.Activity.survey.SurveyPhotoFragment.change;
import static com.crashlytics.android.Crashlytics.log;

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
    private String icNumber;

    String[] parliment;
    String[] category;
    Realm realm;

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

        setupBlock(getActivity(), block6);
        setData();

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

                                rController.surveyLocalStorageS5(context, randomID, getDate(), "Completed", "", null);
                                sDialog.dismiss();

                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                getActivity().startActivity(intent);

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

                                initiateLoadingMsg(getActivity(), "Submitting Survey...");

                                realm = rController.getRealmInstanceContext(context);

                                try {

                                    RealmResults<UserInfoCached> infoCached = realm.where(UserInfoCached.class).findAll();
                                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                                    if (infoCached.size() > 0) {

                                        Gson gson = new Gson();
                                        UserInfoReceive obj = gson.fromJson(infoCached.get(0).getUserInfoString(), UserInfoReceive.class);
                                        icNumber = obj.getData().getIcNumber();

                                        PostSurveyRequest postSurveyRequest = new PostSurveyRequest();
                                        postSurveyRequest.setIcNumber(icNumber);

                                        if (status.equalsIgnoreCase("EDIT_API")) {
                                            Log.e("status", status);
                                            Log.e("status", randomID);

                                            postSurveyRequest.setId(randomID);
                                        }


                                        parliment = survey.getSurveyParliment().split("/");
                                        category = survey.getSurveyCategory().split("/");

                                        Content content = new Content();
                                        content.setCategoryid(category[1]);
                                        content.setCategoryName(category[0]);
                                        content.setIssue(survey.getSurveyIssue());
                                        content.setWishlist(survey.getSurveyWishlist());

                                        gson = new GsonBuilder().disableHtmlEscaping().create();
                                        String stringContent = gson.toJson(content);

                                        //HashMap<String, Object> dicMap = new HashMap<String, Object>();
                                        //dicMap.put("content[]", content);

                                        //contents.add(content);

                                        postSurveyRequest.setLocationCode(parliment[1]);
                                        postSurveyRequest.setLocationName(parliment[0]);
                                        postSurveyRequest.setLocationType("PAR");
                                        postSurveyRequest.setContent(stringContent);
                                        postSurveyRequest.setToken(preferences.getString("temp_token", ""));
                                        //postSurveyRequest.setId(randomID);
                                        presenter.onPostSurvey(postSurveyRequest);
                                    }


                                } finally {
                                    //realm.close();
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

        realm = rController.getRealmInstanceContext(context);

        //try fetch realm data.
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

        } finally {
            //realm.close();
        }

    }

    public void updateStatusData() {

        //try fetch realm data.
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


        if (postSurveyReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                dismissLoading();

                //save info to realm with proper id
                //setSuccess(getActivity(), "Success.", "Survey successfully saved.");
                rController.surveyLocalStorageS5(context, randomID, postSurveyReceive.getData().getUpdated_at(), "Completed", "API-STATUS", postSurveyReceive.getData().getId());
                randomID = postSurveyReceive.getData().getId();

                //get saved photo
                List<MultipartBody.Part> listMultipart = new ArrayList<>();
                String imageList = "";
                try {
                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", postSurveyReceive.getData().getId()).findFirst();
                    imageList = survey.getImagePath();
                    Log.e("no_image", "a" + imageList);
                    if (!imageList.equalsIgnoreCase("")) {
                        String[] parts = imageList.split("___");
                        for (int b = 0; b < parts.length; b++) {
                            if (parts[b] != null) {
                                if (!parts[b].contains("http")) {

                                    Log.e("all_path", parts[b]);
                                    Uri myUri = Uri.parse(parts[b]);
                                    listMultipart.add(prepareFilePart("photos[]", myUri));

                                }


                            }
                        }
                    }


                } catch (Exception e) {
                    Log.e("ERROR_MSG", e.getMessage());
                }

                if(change == null){
                    change = false;
                }

                if (!imageList.equalsIgnoreCase("") && change) {
                    //submit photo.
                    SurveyPhotoContentRequest surveyPhotoContentRequest = new SurveyPhotoContentRequest();
                    surveyPhotoContentRequest.setIcnumber(icNumber);
                    surveyPhotoContentRequest.setLocationCode(parliment[1]);
                    surveyPhotoContentRequest.setLocationName(parliment[0]);
                    surveyPhotoContentRequest.setLocationName(parliment[0]);
                    surveyPhotoContentRequest.setLocationName(parliment[0]);

                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    String stringContent = gson.toJson(surveyPhotoContentRequest);

                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("icnumber", toRequestBody(icNumber));
                    map.put("locationCode", toRequestBody(parliment[1]));
                    map.put("locationName", toRequestBody(parliment[0]));
                    map.put("locationType", toRequestBody("PAR"));
                    map.put("id", toRequestBody(postSurveyReceive.getData().getId()));

                    initiateLoadingMsg(getActivity(), "Uploading Photo...");
                    SurveyPhotoRequest surveyPhotoRequest = new SurveyPhotoRequest();
                    surveyPhotoRequest.setStringContent(stringContent);
                    surveyPhotoRequest.setMap(map);
                    surveyPhotoRequest.setParts(listMultipart);
                    surveyPhotoRequest.setToken(preferences.getString("temp_token", ""));

                    presenter.onSurveyPhotoRequest(surveyPhotoRequest);
                } else {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Success.")
                            .setContentText("Survey successfully saved.")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismiss();

                                    Intent intent = new Intent(getActivity(), TabActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                    getActivity().startActivity(intent);

                                }
                            })
                            .show();

                }


            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = postSurveyReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    @Subscribe
    public void onSurveyPhotoReceive(SurveyPhotoReceive surveyPhotoReceive) {

        dismissLoading();
        if (surveyPhotoReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Success.")
                        .setContentText("Survey successfully saved.")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.dismiss();

                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("ROLE", preferences.getString("user_role", ""));
                                getActivity().startActivity(intent);

                            }
                        })
                        .show();

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = surveyPhotoReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    public RequestBody toRequestBody(String val) {

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), val);
        return body;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        Log.e("X", fileUri.toString());
        File file = new File(fileUri.getPath());
        Log.e("X", Long.toString(file.length()));

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(null, file);
//MediaType.parse(getActivity().getContentResolver().getType(fileUri)
        // MultipartBody.Part is used to send also the actual file name

        //if (fileUri.getPath().contains("imageDir")) {
        //    File myImageFile = new File(fileUri.getPath());
        //    if (myImageFile.delete()) log("image on the disk deleted successfully!");
        //}

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
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

