package com.app.ecosurvey.ui.Activity.survey;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.app.ecosurvey.MainFragmentActivity;
import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class SurveyReviewActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        //setTitle(getResources().getString(R.string.TBD_app_name));
        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, SurveyReviewFragment.newInstance(bundle)).commit();

        normal_with_title_and_back("Survey Review");

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }

}
