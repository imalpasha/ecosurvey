package com.app.ecosurvey.ui.Activity.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.app.ecosurvey.MainFragmentActivity;
import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.homepage.SlidePage.SlidingTabLayout;
import com.app.ecosurvey.ui.Activity.homepage.SlidePage.ViewPagerAdapter;
import com.app.ecosurvey.ui.Realm.RealmController;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class TabActivity extends MainFragmentActivity {

    @Inject
    Bus bus;

    @Inject
    SharedPreferences preferences;

    // Declaring Your View and Variables
    static ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    String point;
    private static Boolean normalFlow = true;

    int Numboftabs = 3;
    String[] title;

    Activity act;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment);
        normal_with_title("Eco Survey");

        preferences = this.getSharedPreferences("SurveyPreferences", Context.MODE_PRIVATE);


        try {

            Bundle bundle = getIntent().getExtras();
            role = bundle.getString("ROLE");
            Log.e("Role",role);

            //save to pref.
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user_role", role);
            editor.apply();

            if (role.equalsIgnoreCase("ParlimentSurveyor")) {
                Numboftabs = 2;
                title = new String[]{"Profile", "My Survey"};
            } else {
                Numboftabs = 3;
                title = new String[]{"Profile", "My Survey", "Checklist"};
            }

        } catch (Exception e) {
            Log.e("Null", "Y");

        }


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), title, Numboftabs, getContext(), this);

        // Assigning ViewPager View and setting the adapter
        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);

        // Assigning the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs);

        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custom_tab, 0);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.default_theme_colour);
            }
        });

        //hideLeftPart();

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        setPager(1);

    }

    public static void setPager(int position) {
        pager.setCurrentItem(position);
    }

    public static ViewPager getPagerInstance() {
        return pager;
    }

    /*public void exitApp() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.home_exit))
                .setContentText(getString(R.string.home_confirm_exit))
                .showCancelButton(true)
                .setCancelText(getString(R.string.home_cancel))
                .setConfirmText(getString(R.string.home_close))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        finishAffinity();
                        finish();
                        System.exit(0);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

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
