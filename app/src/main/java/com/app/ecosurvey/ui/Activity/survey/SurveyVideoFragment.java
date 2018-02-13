package com.app.ecosurvey.ui.Activity.survey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecosurvey.MainController;
import com.app.ecosurvey.R;
import com.app.ecosurvey.application.MainApplication;
import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.ui.Activity.adapter.VideoListAdapter;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedVideoPath;
import com.app.ecosurvey.ui.Model.Realm.Object.LocalSurvey;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.LoginReceive;
import com.app.ecosurvey.ui.Model.Receive.PhotoReceive;
import com.app.ecosurvey.ui.Model.Receive.VideoReceive;
import com.app.ecosurvey.ui.Model.Request.PhotoRequest;
import com.app.ecosurvey.ui.Model.Request.VideoRequest;
import com.app.ecosurvey.ui.Presenter.MainPresenter;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.app.ecosurvey.utils.Utils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

import static android.app.Activity.RESULT_OK;

public class SurveyVideoFragment extends BaseFragment {

    private int fragmentContainerId;
    static final int REQUEST_VIDEO_CAPTURE = 0;

    @Inject
    MainPresenter presenter;

    @Inject
    Bus bus;

    @Inject
    RealmController rController;

    @Inject
    SharedPreferences preferences;

    @Inject
    Context context;

    @Bind(R.id.videoBtnNext)
    Button videoBtnNext;

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
    private int SELECT_FILE = 1;
    private int CHANGE_FILE = 2;
    private static final String GALLERY_CONFIG = "GALLERY_CONFIG";
    private View view;
    private VideoListAdapter adapter;
    private int changeImagePosition;
    private ArrayList<SelectedVideoPath> list = new ArrayList<SelectedVideoPath>();
    private ArrayList<SelectedVideoPath> secondlist = new ArrayList<SelectedVideoPath>();

    private Boolean changeImageTrue = false;
    private Realm realm;
    public static Boolean videoChange;

    public static SurveyVideoFragment newInstance(Bundle bundle) {

        SurveyVideoFragment fragment = new SurveyVideoFragment();
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

        view = inflater.inflate(R.layout.survey_video, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        randomID = bundle.getString("LocalSurveyID");
        status = bundle.getString("Status");
        preferences = getActivity().getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);
        realm = rController.getRealmInstanceContext(context);

        setupBlock(getActivity(), block5);
        autoFill2();

        videoBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String videoList = "";

                    String imageList = "";
                    if (secondlist.size() > 0) {
                        for (int x = 0; x < secondlist.size(); x++) {
                            videoList += secondlist.get(x).getVideoPath() + "___";
                        }
                    }

                    rController.surveyLocalStorageS6(context, randomID, videoList);


                } catch (Exception e) {
                    Log.e("SaveImage", "Error: " + e.getMessage());
                } finally {

                    Intent intent = new Intent(getActivity(), SurveyReviewActivity.class);
                    intent.putExtra("LocalSurveyID", randomID);
                    intent.putExtra("Status", status);
                    getActivity().startActivity(intent);
                }
            }
        });

        openImageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                // Toast.makeText(getActivity(),"Under Maintenance",Toast.LENGTH_SHORT).show();
                Log.e("toast", "Y");
            }
        });

        openImageActionSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                //Toast.makeText(getActivity(),"Under Maintenance",Toast.LENGTH_SHORT).show();
            }
        });

        //init camera & gallery
        captureImageInitialization();

        if (status != null) {
            if (status.equalsIgnoreCase("EDIT") || status.equalsIgnoreCase("EDIT_API")) {

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
                realm = rController.getRealmInstanceContext(context);
                try {
                    LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                    String videoList = survey.getVideoPath();

                    if (videoList != null && !videoList.equalsIgnoreCase("")) {
                        String[] parts = videoList.split("___");
                        //insert path to object
                        for (int x = 0; x < parts.length; x++) {
                            SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                            selectedVideoPath.setVideoPath(parts[x]);
                            selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(x));

                            list.add(selectedVideoPath);
                            secondlist.add(selectedVideoPath);
                        }

                        initiateVideoAdapter(list);
                    }

                } finally {
                    // realm.close();
                }

            } else if (status.equalsIgnoreCase("EDIT_API")) {
                //survey from api.since api data more updated.
                //nd tp call image to validate with local image.
                //later check internet connection
                if (MainController.connectionAvailable(getActivity())) {
                    loadVideoFromAPI();
                } else {
                    try {
                        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

                        String videoList = survey.getVideoPath();

                        if (videoList != null && !videoList.equalsIgnoreCase("")) {
                            String[] parts = videoList.split("___");
                            //insert path to object
                            for (int x = 0; x < parts.length; x++) {
                                SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                                selectedVideoPath.setVideoPath(parts[x]);
                                selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(x));

                                list.add(selectedVideoPath);
                                secondlist.add(selectedVideoPath);
                            }

                            initiateVideoAdapter(list);
                        }

                    } finally {
                        // realm.close();
                    }
                }

            }
        }

    }

    @Subscribe
    public void onVideoReceive(VideoReceive videoReceive) {

        dismissLoading();
        //////////

        //compare api date with local date.
        LocalSurvey survey = realm.where(LocalSurvey.class).equalTo("localSurveyID", randomID).findFirst();

        if (survey.getPhotoUpdateDate() != null) {
            Log.e("LOCAL_DATE", survey.getPhotoUpdateDate());
        }

        if (videoReceive.getApiStatus().equalsIgnoreCase("Y")) {
            try {

                //wait
                dismissLoading();

                Date date = null, date2 = null;
                if (survey.getVideoUpdateDate() != null) {

                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        date = format.parse(survey.getVideoUpdateDate());
                        date2 = format.parse(videoReceive.getData().getUpdated_at());

                    } catch (Exception e) {
                        Log.e("date", "a" + date);
                        Log.e("date2", "b" + date2);
                        Log.e("nuLL", "Y");
                    }


                    if (date != null && date2 != null) {
                        if (date2.after(date)) {

                            //if (photoReceive.getData().getUpdated_at() > survey.getPhotoUpdateDate()) {
                            for (int x = 0; x < videoReceive.getData().getContent().getVideos().size(); x++) {
                                SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                                selectedVideoPath.setVideoPath(videoReceive.getData().getContent().getVideos().get(x));
                                selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(x));
                                list.add(selectedVideoPath);
                            }

                        } else {

                            String imageList = survey.getImagePath();
                            if (imageList != null && !imageList.equalsIgnoreCase("")) {
                                String[] parts = imageList.split("___");
                                //insert path to object
                                for (int x = 0; x < parts.length; x++) {
                                    SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                                    selectedVideoPath.setVideoPath(parts[x]);
                                    selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(x));
                                    list.add(selectedVideoPath);
                                }
                            }
                        }
                    } else {
                        Log.e("Date", "Null");
                    }


                } else {
                    //if (photoReceive.getData().getUpdated_at() > survey.getPhotoUpdateDate()) {
                    if (videoReceive.getData().getContent() != null) {
                        for (int x = 0; x < videoReceive.getData().getContent().getVideos().size(); x++) {
                            SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                            selectedVideoPath.setVideoPath(videoReceive.getData().getContent().getVideos().get(x));
                            selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(x));
                            list.add(selectedVideoPath);
                        }
                    }
                }
                initiateVideoAdapter(list);


            } catch (Exception e) {
                e.printStackTrace();
                setAlertDialog(getActivity(), getString(R.string.err_title), "Read Error");
            } finally {
                //realm.close();
            }

        } else {

            String error_msg = videoReceive.getMessage();
            setAlertDialog(getActivity(), getString(R.string.err_title), error_msg);

        }

    }

    public void loadVideoFromAPI() {

        String token = preferences.getString("temp_token", "");
        initiateLoadingMsg(getActivity(), "Fetching video...");

        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setToken(token);
        videoRequest.setUrl("/api/v1/surveys/videos/" + randomID);
        presenter.onVideoRequest(videoRequest);

    }

    private void dispatchTakeVideoIntent() {

        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
    }

    private void captureImageInitialization() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.init_video, null);

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
                                dispatchTakeVideoIntent();
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

    public void onCaptureCamera(Intent data) {
       /* Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");*/
        Log.e("GetCameraPath", String.valueOf(data.getData()));
        /*FileOutputStream fo;
        destination.createNewFile();
        fo = new FileOutputStream(destination);
        fo.write(bytes.toByteArray());
        fo.close();*/

        SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
        selectedVideoPath.setVideoPath(String.valueOf(data.getData()));
        selectedVideoPath.setRandomPathCode("xxx");

        rController.surveyVideoUpdate(context, randomID, getDate());

        if (list.size() == 0) {
            list.add(selectedVideoPath);
            initiateVideoAdapter(list);
        } else {
            if (changeImageTrue) {
                list.get(changeImagePosition).setVideoPath(String.valueOf(data.getData()));
                list.get(changeImagePosition).setRandomPathCode("xxx" + Integer.toString(0));

                adapter.retrieveNewObject(changeImagePosition, list);
            } else {
                list.add(selectedVideoPath);
                adapter.retrieveNewObject(null, list);
            }

        }


    }

    //gallery feature
    private void galleryIntent() {

        //Intent intent = new Intent();
        //intent.setType("video*//*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent,"Select Video"),SELECT_FILE);*/

        if (changeImageTrue) {

            GalleryConfig config = new GalleryConfig.Build()
                    .singlePhoto(true)
                    .filterMimeTypes(new String[]{"image/jpeg", "image/png"})
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
                    .filterMimeTypes(new String[]{"image/jpeg", "image/png"})
                    .build();

            //GalleryActivityV2.openActivity(getActivity(), SELECT_FILE, config);
            Intent intent = new Intent(getActivity(), GalleryActivityV2.class);
            intent.putExtra(GALLERY_CONFIG, config);
            startActivityForResult(intent, SELECT_FILE);
            changeImageTrue = false;
        }

        changeImageTrue = false;

    }

    public void initiateVideoAdapter(ArrayList<SelectedVideoPath> array) {

        setImageBlock1.setVisibility(View.GONE);
        setImageBlock2.setVisibility(View.VISIBLE);

        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new VideoListAdapter(SurveyVideoFragment.this, array, getActivity());

        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(MyLayoutManager);

    }

    public void enableVideoSelection() {
        setImageBlock1.setVisibility(View.VISIBLE);
        setImageBlock2.setVisibility(View.GONE);
    }

    public void reselectVideo(Integer pos) {

        dialog.show();
        changeImageTrue = true;
        changeImagePosition = pos;

    }

    public void setVideoPathForHttp(String path) {

        Log.e("filepath", path);

        SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
        selectedVideoPath.setVideoPath(path);
        selectedVideoPath.setRandomPathCode("xxx");
        secondlist.add(selectedVideoPath);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                //list of videos of selected
                videoChange = true;
                //List<String> videos = (List<String>) data.getSerializableExtra(GalleryActivityV2.VIDEO);
                String videos = (String) data.getSerializableExtra(GalleryActivityV2.VIDEO);

                //insert path to object
                //for (int x = 0; x < videos.size(); x++) {
                SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                selectedVideoPath.setVideoPath(videos);
                selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(1));
                list.add(selectedVideoPath);
                secondlist.add(selectedVideoPath);

                //}

                if (true) {

                    rController.surveyVideoUpdate(context, randomID, getDate());
                    initiateVideoAdapter(list);
                }

                //list of videos of selected
                //List<String> videos = (List<String>) data.getSerializableExtra(GalleryActivityV2.VIDEO);
            } else if (requestCode == CHANGE_FILE) {

                videoChange = true;

                //List<String> videos = (List<String>) data.getSerializableExtra(GalleryActivityV2.VIDEO);
                //Log.e("xxxxx", "a" + videos.get(0));
                String videos = (String) data.getSerializableExtra(GalleryActivityV2.VIDEO);

                SelectedVideoPath selectedVideoPath = new SelectedVideoPath();
                selectedVideoPath.setVideoPath(videos);
                selectedVideoPath.setRandomPathCode("xxx" + Integer.toString(1));

                //list.remove(changeImagePosition);
                list.get(changeImagePosition).setVideoPath(videos);
                list.get(changeImagePosition).setRandomPathCode("xxx" + Integer.toString(0));

                secondlist.get(changeImagePosition).setVideoPath(videos);
                secondlist.get(changeImagePosition).setRandomPathCode("xxx" + Integer.toString(0));

                rController.surveyVideoUpdate(context, randomID, getDate());
                adapter.retrieveNewObject(changeImagePosition, list);

            } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
                onCaptureCamera(data);
            }
        } else {
            Log.e("STATUS UPLOAD", "Image not uploaded");
        }


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

