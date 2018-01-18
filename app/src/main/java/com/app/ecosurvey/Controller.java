package com.app.ecosurvey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.app.ecosurvey.base.BaseFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dell on 1/5/2016.
 */
public class Controller extends BaseFragment {

    private static SweetAlertDialog pDialog;


    public static void clickableBannerWithURL(Activity act, String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        act.startActivity(i);

        Log.e("url", url);

    }



    public static boolean getRequestStatus(String objStatus,String message, Activity act) {

        Boolean status = false;
        if (objStatus.equals("success") || objStatus.equals("Redirect")) {
            status = true;

        } else if (objStatus.equals("error") || objStatus.equals("error_validation")) {
            status = false;
            setAlertDialog(act, message, "Error");

        }
        return status;

    }

}
