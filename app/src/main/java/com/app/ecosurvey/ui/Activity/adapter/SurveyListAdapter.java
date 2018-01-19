package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.homepage.MySurveyFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SurveyList;
import com.app.ecosurvey.utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SurveyListAdapter  extends BaseAdapter {

    private final Activity context;
    private MySurveyFragment frag;
    private final List<SurveyList> obj;

    public SurveyListAdapter(Activity context, MySurveyFragment fragment, List<SurveyList> obj) {
        this.context = context;
        this.frag = fragment;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        Log.e("Size size", String.valueOf(obj.size()));
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @Bind(R.id.survey_category)
        TextView survey_category;

        @Bind(R.id.survey_issue)
        TextView survey_issue;

        @Bind(R.id.survey_created)
        TextView survey_created;

        @Bind(R.id.survey_layout)
        LinearLayout survey_layout;

        @Bind(R.id.survey_status)
        TextView survey_status;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        final ViewHolder vh;

        view = LayoutInflater.from(context).inflate(R.layout.survey_list, parent, false);
        vh = new ViewHolder();
        ButterKnife.bind(vh, view);
        view.setTag(vh);

        String c = obj.get(position).getSurveyParliment() + " " + obj.get(position).getSurveyCategory();
        String i = obj.get(position).getSurveyIssue();
        String s = obj.get(position).getSurveyStatus();
        String d = obj.get(position).getStatusCreated();

        vh.survey_category.setText(c);
        vh.survey_issue.setText(i);
        vh.survey_status.setText(s);

        /*Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => "+calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String formattedDate = df.format(calendar.getTime());*/

        vh.survey_created.setText("Created on " + d);

        vh.survey_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.editData(obj.get(position).getLocalSurveyID());
            }
        });


        return view;
    }
}
