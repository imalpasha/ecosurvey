package com.app.ecosurvey.ui.Activity.survey;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.ecosurvey.MainFragmentActivity;
import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class SurveyPhotoActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, SurveyPhotoFragment.newInstance(bundle)).commit();

        normal_with_title_and_back("Gambar Survey");

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }

}
