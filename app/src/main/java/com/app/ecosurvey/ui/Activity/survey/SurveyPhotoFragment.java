package com.app.ecosurvey.ui.Activity.survey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.PhotoReceive;
import com.app.ecosurvey.ui.Model.Request.PhotoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Activity.adapter.ImageListAdapter;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.Utils;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SurveyPhotoFragment extends BaseFragment {

    private int fragmentContainerId;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;

    @Inject
    RealmController rController;

    @Inject
    Context context;

    @Bind(R.id.photoBtnNext)
    Button photoBtnNext;

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

    @Bind(R.id.openImageAction)
    LinearLayout openImageAction;

    @Bind(R.id.openImageActionSmall)
    LinearLayout openImageActionSmall;

    @Bind(R.id.setImageBlock1)
    LinearLayout setImageBlock1;

    @Bind(R.id.setImageBlock2)
    LinearLayout setImageBlock2;

    private String randomID;
    private String status;

    private AlertDialog dialog;
    private String userChoosenTask;
    private Boolean result;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private int CHANGE_FILE = 2;
    private static final String GALLERY_CONFIG = "GALLERY_CONFIG";
    private View view;
    private ImageListAdapter adapter;
    private int changeImagePosition;
    private ArrayList<SelectedImagePath> list = new ArrayList<SelectedImagePath>();
    private Boolean changeImageTrue = false;

    public static SurveyPhotoFragment newInstance(Bundle bundle) {

        SurveyPhotoFragment fragment = new SurveyPhotoFragment();
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

        view = inflater.inflate(R.layout.survey_photo, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");
        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);

        setupBlock(getActivity(), block4);
        autoFill2();

        //try fetch realm data.
        Realm realm = rController.getRealmInstanceContext(context);
        try {
            LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();
            Log.e("SurveyCategory", survey.getSurveyCategory());
            Log.e("SurveyParliment", survey.getSurveyParliment());
            Log.e("SurveyIssue", survey.getSurveyIssue());
            Log.e("SurveyWishlist", survey.getSurveyWishlist());
            Log.e("SurveyLocalProgress", survey.getSurveyLocalProgress());

        } finally {
            realm.close();
        }

        photoBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TEST", "NEXT_CLICK");
                //save list of photo
                try {

                    String imageList = "";

                    //Gson gsonUserInfo = new Gson();
                    //String gsonImage = gsonUserInfo.toJson(list);
                    for (int x = 0; x < list.size(); x++) {
                        imageList += list.get(x).getImagePath() + "___";
                    }

                    rController.surveyLocalStorageS4(context, randomID, imageList);
                    Log.e("TEST", "LIST_NOT_NULL");
/*
                    if (list != null) {
                        Gson gsonUserInfo = new Gson();
                        String gsonImage = gsonUserInfo.toJson(list);

                        Log.e("TEST_GSON_IMAGE", gsonImage);
                        rController.surveyLocalStorageS4(context, randomID, gsonImage);

                        Log.e("TEST", "DONE_S4_REALM");
                        Intent intent = new Intent(getActivity(), SurveyVideoActivity.class);
                        intent.putExtra("LocalSurveyID", randomID);
                        intent.putExtra("Status", status);
                        getActivity().startActivity(intent);

                    } else {
                        Log.e("TEST", "LIST_NULL");
                    }
*/

                } catch (Exception e) {

                    Log.e("SaveImage", "Error: " + e.getMessage());
                } finally {

                    Intent intent = new Intent(getActivity(), SurveyVideoActivity.class);
                    intent.putExtra("LocalSurveyID", randomID);
                    intent.putExtra("Status", status);
                    getActivity().startActivity(intent);
                }

                /*initiateLoading(getActivity());
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserID(txtAuthID.getText().toString());
                loginRequest.setUserPassword(txtAuthPassword.getText().toString());
                presenter.onLoginRequest(loginRequest);*/
            }
        });

        openImageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

        openImageActionSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

        //init camera & gallery
        captureImageInitialization();

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

                block6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SurveyReviewActivity.class);
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

    public void autoFill2() {

        if (status != null) {
            if (status.equalsIgnoreCase("EDIT")) {

                //try fetch realm data.
                Log.e("edit", "edit");
                Realm realm = rController.getRealmInstanceContext(context);
                try {
                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                    String imageList = survey.getImagePath();

                    if (imageList != null && !imageList.equalsIgnoreCase("")) {
                        String[] parts = imageList.split("___");
                        //insert path to object
                        for (int x = 0; x < parts.length; x++) {
                            SelectedImagePath selectedImagePath = new SelectedImagePath();
                            selectedImagePath.setImagePath(parts[x]);
                            selectedImagePath.setRandomPathCode("xxx" + Integer.toString(x));
                            Log.e("pathpath", parts[x]);
                            list.add(selectedImagePath);
                        }

                        initiateImageAdapter(list);
                    } else {

                        //need to verify photo_updated_date before call this.
                        loadImageFromAPI();
                    }

                    //txtSurveyIssue.setText(survey.getSurveyIssue());

                } finally {
                    realm.close();
                }

            }
        }

    }

    public void loadImageFromAPI() {

        String token = preferences.getString("temp_token", "");

        initiateLoading(getActivity());

        PhotoRequest photoRequest = new PhotoRequest();
        photoRequest.setToken(token);
        photoRequest.setUrl("/api/v1/surveys/photos/" + randomID);
        presenter.onPhotoRequest(photoRequest);

    }

    private void captureImageInitialization() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.init_image, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        final TextView btnGallery = (TextView) myView.findViewById(R.id.btn_select_gallery);
        final TextView btnCamera = (TextView) myView.findViewById(R.id.btn_take_camera);
        TextView btnCancel = (TextView) myView.findViewById(R.id.btn_cancel);

        dialog = builder.create();

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                userChoosenTask = "Choose Photo";
                btnGallery.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionGallery(getActivity());
                            if (result) {
                                galleryIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Gallery", "CLOSE");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userChoosenTask = "Take Photo";
                btnCamera.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = Utils.checkPermissionCamera(getActivity());
                            if (result) {
                                cameraIntent();
                                dialog.dismiss();
                            } else {
                                Log.e("Camera", "CLOSE");
                            }
                        } catch (Exception e) {

                        }
                    }
                });

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1070;
        dialog.getWindow().setAttributes(lp);

    }

    //camera feature
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //gallery feature
    private void galleryIntent() {

        if (changeImageTrue) {

            GalleryConfig config = new GalleryConfig.Build()
                    .singlePhoto(true)
                    .filterMimeTypes(new String[]{"image/jpeg"})
                    .build();

            //GalleryActivityV2.openActivity(getActivity(), SELECT_FILE, config);
            Intent intent = new Intent(getActivity(), GalleryActivityV2.class);
            intent.putExtra(GALLERY_CONFIG, config);
            startActivityForResult(intent, CHANGE_FILE);

        } else {

            GalleryConfig config = new GalleryConfig.Build()
                    .limitPickPhoto(5)
                    .singlePhoto(false)
                    .hintOfPick("Maximum image is 5")
                    .filterMimeTypes(new String[]{"image/jpeg"})
                    .build();

            //GalleryActivityV2.openActivity(getActivity(), SELECT_FILE, config);
            Intent intent = new Intent(getActivity(), GalleryActivityV2.class);
            intent.putExtra(GALLERY_CONFIG, config);
            startActivityForResult(intent, SELECT_FILE);
            changeImageTrue = false;
        }

        changeImageTrue = false;

    }

    public void initiateImageAdapter(ArrayList<SelectedImagePath> array) {

        setImageBlock1.setVisibility(View.GONE);
        setImageBlock2.setVisibility(View.VISIBLE);

        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new ImageListAdapter(SurveyPhotoFragment.this, array, getActivity());

        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(MyLayoutManager);

    }

    public void enablePhotoSelection() {
        setImageBlock1.setVisibility(View.VISIBLE);
        setImageBlock2.setVisibility(View.GONE);
    }

    public void reselectImage(Integer pos) {

        dialog.show();
        changeImageTrue = true;
        changeImagePosition = pos;

    }


    @Subscribe
    public void onPhotoReceive(PhotoReceive photoReceive) {

        dismissLoading();

        if (photoReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                for (int x = 0; x < photoReceive.getData().getContent().getImages().size(); x++) {
                    SelectedImagePath selectedImagePath = new SelectedImagePath();
                    selectedImagePath.setImagePath(photoReceive.getData().getContent().getImages().get(x));
                    selectedImagePath.setRandomPathCode("xxx" + Integer.toString(x));
                    Log.e("pathpath", photoReceive.getData().getContent().getImages().get(x));
                    list.add(selectedImagePath);
                }

                initiateImageAdapter(list);

            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            }

        } else {

            String error_msg = photoReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }


    }

    //process result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("RequestCode", "Here");
        Log.e("RequestCode", Integer.toString(requestCode));
        Log.e("ResultCode", Integer.toString(resultCode));

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                //list of photos of selected

                List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivityV2.PHOTOS);

                //insert path to object
                for (int x = 0; x < photos.size(); x++) {
                    SelectedImagePath selectedImagePath = new SelectedImagePath();
                    selectedImagePath.setImagePath(photos.get(x));
                    selectedImagePath.setRandomPathCode("xxx" + Integer.toString(x));
                    list.add(selectedImagePath);
                }

                if (true) {
                    initiateImageAdapter(list);
                }

                //list of videos of selected
                //List<String> videos = (List<String>) data.getSerializableExtra(GalleryActivityV2.VIDEO);
            } else if (requestCode == CHANGE_FILE) {

                List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivityV2.PHOTOS);
                Log.e("xxxxx", "a" + photos.get(0));

                SelectedImagePath selectedImagePath = new SelectedImagePath();
                selectedImagePath.setImagePath(photos.get(0));
                selectedImagePath.setRandomPathCode("xxx" + Integer.toString(0));

                //list.remove(changeImagePosition);
                list.get(changeImagePosition).setImagePath(photos.get(0));
                list.get(changeImagePosition).setRandomPathCode("xxx" + Integer.toString(0));

                adapter.retrieveNewObject(changeImagePosition, list);

            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureCamera(data);
            }
        } else {
            Log.e("STATUS UPLOAD", "Image not uploaded");
        }

    }

    public void onCaptureCamera(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Log.e("GetCameraPath", destination.toString());
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

            SelectedImagePath selectedImagePath = new SelectedImagePath();
            selectedImagePath.setImagePath(destination.toString());
            selectedImagePath.setRandomPathCode("xxx");

            if (list.size() == 0) {
                list.add(selectedImagePath);
                initiateImageAdapter(list);
            } else {
                if (changeImageTrue) {
                    list.get(changeImagePosition).setImagePath(destination.toString());
                    list.get(changeImagePosition).setRandomPathCode("xxx" + Integer.toString(0));

                    adapter.retrieveNewObject(changeImagePosition, list);
                } else {
                    list.add(selectedImagePath);
                    adapter.retrieveNewObject(null, list);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //-----------------------------------CONVERT IMAGE TO 64BASE--------------------------------------//


    /*public String changeImage(Bitmap bitmap) {
        *//*BitmapDrawable drawable = (BitmapDrawable) myImgUserDP.getDrawable();
        Bitmap bitmap = drawable.getBitmap();*//*

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();

        return Base64.encodeToString(bb, 0);
    }*/


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

