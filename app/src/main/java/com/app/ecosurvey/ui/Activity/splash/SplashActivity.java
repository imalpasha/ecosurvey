package com.app.ecosurvey.ui.Activity.splash;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.ecosurvey.MainFragmentActivity;
import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class SplashActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        //setTitle(getResources().getString(R.string.TBD_app_name));
        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, SplashFragment.newInstance(bundle)).commit();

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }

}
