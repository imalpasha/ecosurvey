package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.homepage.MySurveyFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SurveyList;
import com.app.ecosurvey.utils.SharedPrefManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SurveyListAdapter  extends RecyclerView.Adapter<SurveyListAdapter.MyViewHolder> {

    private final Activity context;
    private MySurveyFragment frag;
    private final List<SurveyList> obj;

    public SurveyListAdapter(Activity context, MySurveyFragment fragment, List<SurveyList> obj) {
        this.context = context;
        this.frag = fragment;
        this.obj = obj;
    }

    @Override
    public SurveyListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.survey_list, parent, false);

        SurveyListAdapter.MyViewHolder holder = new SurveyListAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final SurveyListAdapter.MyViewHolder holder, final int position) {

        String c = obj.get(position).getSurveyParliment() + " " + obj.get(position).getSurveyCategory();
        String i = obj.get(position).getSurveyIssue();
        String s = obj.get(position).getSurveyStatus();
        String d = obj.get(position).getStatusCreated();
        String u = obj.get(position).getStatusUpdated();

        holder.survey_category.setText(c);
        holder.survey_issue.setText(i);
        holder.survey_status.setText(s);

        /*Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => "+calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String formattedDate = df.format(calendar.getTime());*/

        holder.survey_created.setText("Created : " + d);
        holder.survey_updated.setText("Last Updated : " + u);


        holder.survey_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.editData(obj.get(position).getLocalSurveyID());
            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.confirmDelete(position);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView survey_category;
        TextView survey_issue;
        TextView survey_created;
        RelativeLayout survey_layout;
        TextView survey_status;
        TextView survey_updated;
        ImageView btnDelete;


        public MyViewHolder(View insideMeal) {
            super(insideMeal);

            survey_issue = (TextView) insideMeal.findViewById(R.id.survey_issue);
            survey_category = (TextView) insideMeal.findViewById(R.id.survey_category);
            survey_created = (TextView) insideMeal.findViewById(R.id.survey_created);
            survey_status = (TextView) insideMeal.findViewById(R.id.survey_status);
            survey_updated = (TextView) insideMeal.findViewById(R.id.survey_updated);
            btnDelete = (ImageView) insideMeal.findViewById(R.id.btnDelete);
            survey_layout = (RelativeLayout) insideMeal.findViewById(R.id.survey_layout);


        }
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

        @Bind(R.id.survey_updated)
        TextView survey_updated;

        @Bind(R.id.btnDelete)
        TextView btnDelete;

    }


    @Override
    public int getItemCount() {
        return obj.size();
    }




    @Override
    public long getItemId(int position) {
        return 0;
    }


    public void confirmDelete(Integer position){

        obj.remove(position);
        //recycler.removeViewAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, obj.size());
    }
}
