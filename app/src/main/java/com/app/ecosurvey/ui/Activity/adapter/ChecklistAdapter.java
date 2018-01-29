package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.homepage.MyWishlistFragment;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 1/28/2018.
 */

public class ChecklistAdapter extends BaseAdapter {

    private final Activity context;
    private MyWishlistFragment frag;
    private final ChecklistReceive obj;

    public String statusImage = "NOT_CLICK";

    public ChecklistAdapter(Activity context, MyWishlistFragment fragment, ChecklistReceive obj) {
        this.context = context;
        this.frag = fragment;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        Log.e("Size size", String.valueOf(obj.getData().size()));
        return obj == null ? 0 : obj.getData().size();
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

        @Bind(R.id.checked_image)
        ImageView checked_image;

        @Bind(R.id.checklist_title)
        TextView checklist_title;

        @Bind(R.id.checklist_cont)
        TextView checklist_cont;

        @Bind(R.id.container)
        LinearLayout container;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        final ViewHolder vh;

        view = LayoutInflater.from(context).inflate(R.layout.checklist_list, parent, false);
        vh = new ViewHolder();
        ButterKnife.bind(vh, view);
        view.setTag(vh);

        String t = obj.getData().get(position).getChecklist_text();
        String c = obj.getData().get(position).getComment();
        /*String s = obj.get(position).getSurveyStatus();
        String d = obj.get(position).getStatusCreated();
        String u = obj.get(position).getStatusUpdated();

        vh.survey_category.setText(c);*/
        vh.checklist_title.setText(t);
        vh.checklist_cont.setText(c);

        /*Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => "+calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String formattedDate = df.format(calendar.getTime());*/

        /*vh.survey_created.setText("Created : " + d);
        vh.survey_updated.setText("Last Updated : " + u);


        vh.survey_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.editData(obj.get(position).getLocalSurveyID());
            }
        });*/

        vh.checked_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusImage.equals("NOT_CLICK")) {
                    vh.checked_image.setImageResource(R.drawable.check_64);
                    statusImage = "CLICKED";
                    vh.container.setVisibility(View.VISIBLE);
                    Log.e("Image Status", statusImage);

                } else if (statusImage.equals("CLICKED")) {
                    vh.checked_image.setImageResource(R.drawable.box_64);
                    statusImage = "NOT_CLICK";
                    vh.container.setVisibility(View.GONE);
                    Log.e("Image Status", statusImage);
                }
            }
        });

        return view;
    }
}

