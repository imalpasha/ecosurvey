package com.app.ecosurvey;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.app.ecosurvey.base.BaseFragmentActivity;

import butterknife.ButterKnife;

//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuItem;

public class MainFragmentActivity extends BaseFragmentActivity {

    private static Activity instance;
    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */

    public static Activity setContext(Activity act) {
        instance = act;
        return instance;

    }

    public static Activity getContext() {
        //return instance.getApplicationContext();
        //return ActivityName.this;
        return instance;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);
        ButterKnife.bind(this);
        instance = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();

        // if (isTaskRoot()) {
        // clean the file cache with advance option
        long triggerSize = 300000; // starts cleaning when cache size is
        // larger than 3M
        long targetSize = 200000; // remove the least recently used files
        // until cache size is less than 2M
        AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        // }
    }

    ///  public void hideTabButton() {
    //     aq.id(R.id.tab_container).gone();
    // }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        // actionBar.setDisplayShowTitleEnabled(true);
        // actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                break;

            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

}
