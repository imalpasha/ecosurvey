package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.homepage.MyWishlistFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.CheckList;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 1/28/2018.
 */

public class ChecklistAdapter extends BaseAdapter {

    private final Activity context;
    private MyWishlistFragment frag;
    private final ChecklistReceive obj;
    private String i, t, c;
    List<CheckList> surveyLists;

    public String statusImage;

    public ChecklistAdapter(Activity context, MyWishlistFragment fragment, ChecklistReceive obj) {
        this.context = context;
        this.frag = fragment;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        Log.e("Size size", String.valueOf(obj.getData().getContent().size()));
        return obj == null ? 0 : obj.getData().getContent().size();
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
        EditText checklist_cont;

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

        i = obj.getData().getContent().get(position).getItemid();
        t = obj.getData().getContent().get(position).getComment();
        c = obj.getData().getContent().get(position).getCheck();

        vh.checklist_title.setText("(Item Name) " + i);
        vh.checklist_title.setTag(i);

        vh.checklist_cont.setText(t);

        if (c.equalsIgnoreCase("yes")) {
            vh.container.setVisibility(View.VISIBLE);
            vh.checked_image.setImageResource(R.drawable.check_64);
            vh.checked_image.setTag("Clicked");
        } else {
            vh.container.setVisibility(View.GONE);
            vh.checked_image.setImageResource(R.drawable.box_64);
            vh.checked_image.setTag("NotClicked");
        }

        vh.checked_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vh.checked_image.getTag().equals("NotClicked")) {
                    vh.checked_image.setImageResource(R.drawable.check_64);
                    vh.checked_image.setTag("Clicked");

                    obj.getData().getContent().get(position).setCheck("yes");

                    vh.container.setVisibility(View.VISIBLE);

                } else if (vh.checked_image.getTag().equals("Clicked")) {
                    vh.checked_image.setImageResource(R.drawable.box_64);
                    vh.checked_image.setTag("NotClicked");

                    obj.getData().getContent().get(position).setCheck("no");

                    vh.container.setVisibility(View.GONE);
                }
            }
        });

        vh.checklist_cont.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                Log.e("vh.txtGivenName " + position, arg0.toString());

                if (!arg0.toString().equals("")) {
                    obj.getData().getContent().get(position).setComment(arg0.toString().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

        });

        return view;
    }

    public ChecklistReceive checklistObj() {
        return obj;
    }
}

