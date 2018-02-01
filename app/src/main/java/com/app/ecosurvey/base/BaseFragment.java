package com.app.ecosurvey.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.MainController;
import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.splash.SplashActivity;
import com.app.ecosurvey.utils.DropDownItem;
import com.app.ecosurvey.utils.DropMenuAdapter;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.app.ecosurvey.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

//import com.fly.firefly.ui.adapter.CheckInPassengerListAdapter;


public class BaseFragment extends Fragment {

    public com.app.ecosurvey.base.AQuery aq;
    private SharedPreferences pref;
    private int indexForState = -1;
    private String selected;
    private static SharedPrefManager prefManager;
    private static SpotsDialog mProgressDialog;
    private static SweetAlertDialog pDialog;
    private static Dialog dialog;
    private static Boolean status;
    Boolean manualValidationStatus = true;
    private static int staticIndex = -1;
    private Activity activityContext;
    private static ProgressDialog progressDialog;
    private static int currentLength = 0;
    private static String music;
    /* COMIC */
    static MediaPlayer backgroundMP;
    static MediaPlayer backgroundPageSelection;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        aq = new com.app.ecosurvey.base.AQuery(getActivity());
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefManager = new SharedPrefManager(getActivity());

        //check music
        HashMap<String, String> initPassword = prefManager.getMusic();
        music = initPassword.get(SharedPrefManager.MUSIC);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void setShake(View view) {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        view.startAnimation(shake);
    }

    public static void setAlertMaintance(final Activity act, String msg, final Class<?> cls, String message) {


        new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(message)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent explicitIntent = new Intent(act, cls);
                        explicitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //explicitIntent.putExtra("AlertDialog", "Y");
                        act.startActivity(explicitIntent);
                        act.finish();

                    }
                })
                .show();

    }

    public static void setAlertDialog(final Activity act, String msg, String title) {

        if (act != null) {
            if (!act.isFinishing()) {

                if (act.getClass().getSimpleName().equals("SplashActivity")) {
                    new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(title)
                            .setContentText(msg)
                            .setConfirmText("Retry")
                            .setCancelText(act.getString(R.string.home_close))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent retryActivity = new Intent(act, SplashActivity.class);
                                    retryActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    act.startActivity(retryActivity);
                                    act.overridePendingTransition(0, 0);
                                    act.finish();
                                    sDialog.dismiss();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    act.finish();
                                    sDialog.dismiss();
                                }
                            })
                            .show();

                } else {
                    new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("xx")
                            .setContentText(msg)
                            .show();
                }
            }
        }
    }

    public static void setSuccess(final Activity act, String msg, String title) {

        if (act != null) {
            if (!act.isFinishing()) {
                new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(msg)
                        .show();
            }

        } else {

        }
    }

    public static void initiateLoading(Activity act) {

        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            progressDialog = new ProgressDialog(act);
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

        } catch (Exception e) {

        }

    }


    public static void initiateLoadingMsg(Activity act,String msg) {

        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }

            progressDialog = new ProgressDialog(act);
            progressDialog.show();
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);

        } catch (Exception e) {

        }

    }

    public static void dismissLoading() {

        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        } catch (Exception e) {

        }

        Log.e("Dismiss", "N");
    }

    //popup box using sweatalert dialog box
    public static void setError(final Context act, String title, String message) {

        new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(act.getResources().getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();

                    }
                })
                .show();


    }

    public static void connectionError(Activity act) {

        dismissLoading();
        if (MainController.connectionAvailable(act)) {
            setAlertDialog(act, act.getString(R.string.base_connection_server), act.getString(R.string.base_connection_error));
        } else {
            setAlertDialog(act, act.getString(R.string.base_no_internet), act.getString(R.string.base_connection_error));
        }
    }


    public void editTextMaterial(TextView txt1, final TextView txt2) {

        txt1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0)
                    txt2.setVisibility(View.VISIBLE);
                else
                    txt2.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void setupBlock(Activity act, LinearLayout layout) {
        layout.setBackground(ContextCompat.getDrawable(act, R.drawable.circle_border));
    }

    /*Global PoPup*/
    public void popupSelection(final ArrayList array, Activity act, final TextView txt, final Boolean tagStatus) {

        final ArrayList<DropDownItem> a = array;
        DropMenuAdapter dropState = new DropMenuAdapter(act);
        dropState.setItems(a);

        AlertDialog.Builder alertStateCode = new AlertDialog.Builder(act);

        alertStateCode.setSingleChoiceItems(dropState, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selected = a.get(which).getText();
                String selectedCode = a.get(which).getCode();

                txt.setText(selected);

                if (tagStatus) {
                    txt.setTag(selected + "/" + selectedCode);
                }

                indexForState = which;

                dialog.dismiss();
            }
        });


        AlertDialog mDialog = alertStateCode.create();
        mDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.horizontalMargin = 100;
        //lp.verticalMargin = 100;
        mDialog.getWindow().setAttributes(lp);
    }

    public void hideKeyboard(Activity act, View v) {
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public String getDate(){

        String formattedDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(calendar.getTime());

        //2018-02-01 00:06:43

        return formattedDate;
    }




}
