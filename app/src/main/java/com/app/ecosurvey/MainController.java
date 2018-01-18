package com.app.ecosurvey;

import android.app.Activity;

import com.app.ecosurvey.base.BaseFragment;
import com.app.ecosurvey.utils.SharedPrefManager;

/**
 * Created by Dell on 1/5/2016.
 */
public class MainController extends BaseFragment {

    public static boolean connectionAvailable(Activity act){

        Boolean internet;
        //internet = Utils.isNetworkAvailable(act);

        return true;
    }

    public static boolean getRequestStatus(String objStatus,String message,Activity act) {

        SharedPrefManager pref;

        Boolean status = false;
        if (objStatus.equals("OK") || objStatus.equals("Redirect")) {
            status = true;

        } else if (objStatus.equals("Error") || objStatus.equals("error_validation")) {
            status = false;
            setAlertDialog(act, message,"Error");

        }
        return status;

    }
}
