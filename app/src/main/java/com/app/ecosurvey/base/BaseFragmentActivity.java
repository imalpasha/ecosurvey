package com.app.ecosurvey.base;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.androidquery.AQuery;
import com.app.ecosurvey.R;
import com.app.ecosurvey.utils.App;

public class BaseFragmentActivity extends FragmentActivity {

    public AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //aq = new com.app.tbd.base.AQuery(this);
        aq = new AQuery(this);
        //SET TO PORTRAIT ONLY

        /*if (this.getClass().getSimpleName().equals("MemberCardActivity")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }*/

        try {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setCustomView(R.layout.actionbar);
            View actionBarView = actionBar.getCustomView();
            aq.recycle(actionBarView);
            aq.id(R.id.title).typeface(Typeface.createFromAsset(getAssets(), App.FONT_HELVETICA_NEUE)).textSize(22).textColor(Color.WHITE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Tab bar*/
    /*public void setActionbar_home() {

        View actionBarView = getActionBar().getCustomView();
        mainTab = getActionBar().getCustomView();

        aq.recycle(actionBarView);
        aq.id(R.id.actionbar_home).visible();
        aq.id(R.id.actionbar_normal_title).gone();
        aq.id(R.id.actionbar_normal_with_back).gone();

        HashMap<String, String> initCode = pref.getDarkside();
        String darkside = initCode.get(SharedPrefManager.DARK_SIDE);

        if (darkside != null) {
            if (darkside.equalsIgnoreCase("Y")) {
                aq.id(R.id.tabLogo).image(ContextCompat.getDrawable(this, R.drawable.aab_logo_grey));
            } else {
                aq.id(R.id.tabLogo).image(ContextCompat.getDrawable(this, R.drawable.new_aab_logo_header));
            }
        }


        if (loginReceive.getBigPoint() != null && loginReceive.getBigPoint() != "") {
            aq.id(R.id.txtPtsBalance).text(changeThousands(loginReceive.getBigPoint()));
        }

        setTabPoint(BaseFragmentActivity.this);
    }*/


    public void normal_with_title_and_back(String title) {

        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.mainTitle).text(title);
        aq.id(R.id.backBtn).visible();
        aq.id(R.id.backBtn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void normal_with_title(String title) {

        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.mainTitle).text(title);

    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);


    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void finish() {
        super.finish();
        System.gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.gc();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        onResumeExt();

    }

    public void onResumeExt() {

        if (this.getClass().getSimpleName().equals("LoginActivity")) {
            normal_with_title(getString(R.string.app_name));
        }

    }

}
