package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.app.ecosurvey.ui.Model.Adapter.Object.MergeList;
import com.app.ecosurvey.ui.Model.Receive.CategoryReceive.ChecklistReceive;
import com.app.ecosurvey.ui.Model.Receive.InitChecklistReceive;

import org.w3c.dom.Text;

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
    private ArrayList<MergeList> listsMerge;
    private String i, t, c;
    private int z = 0;

    public String statusImage;

    public ChecklistAdapter(Activity context, MyWishlistFragment fragment, ArrayList<MergeList> listsMerge) {
        this.context = context;
        this.frag = fragment;
        this.listsMerge = listsMerge;
    }

    @Override
    public int getCount() {

        /*int count = 0;
        for (int c = 0; c < oriObj.getData().size(); c++) {
            if (oriObj.getData().get(c).getParent_id().equalsIgnoreCase("0"))
                count++;
        }*/
        Log.e(" listsMerge.size()", Integer.toString(listsMerge.size()));
        return listsMerge == null ? 0 : listsMerge.size();
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

        @Bind(R.id.txtHeader)
        TextView txtHeader;

        @Bind(R.id.hasChildLayout)
        LinearLayout hasChildLayout;

        /*@Bind(R.id.checked_image)
        ImageView checked_image;

        @Bind(R.id.checklist_title)
        TextView checklist_title;

        @Bind(R.id.checklist_cont)
        EditText checklist_cont;

        @Bind(R.id.container)
        LinearLayout container;

        @Bind(R.id.hasChildLayout)
        TextView hasChildLayout;*/

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {


        final ViewHolder vh;

        view = LayoutInflater.from(context).inflate(R.layout.checklist_list, parent, false);
        vh = new ViewHolder();
        ButterKnife.bind(vh, view);
        view.setTag(vh);

        LayoutInflater inflater = context.getLayoutInflater();

        vh.txtHeader.setText(listsMerge.get(position).getHeader());
        if (listsMerge.get(position).getChildLists().size() > 0) {
            z = 0;
            //start_add_child_view
            for (int x = 0; x < listsMerge.get(position).getChildLists().size(); x++) {

                z++;

                View childView = inflater.inflate(R.layout.checklist_child_list, null, false);

                final EditText checklist_com = (EditText) childView.findViewById(R.id.checklist_cont);
                final ImageView checked_image = (ImageView) childView.findViewById(R.id.checked_image);
                TextView checklist_title = (TextView) childView.findViewById(R.id.checklist_title);
                final LinearLayout container = (LinearLayout) childView.findViewById(R.id.container);

                checklist_title.setText(listsMerge.get(position).getChildLists().get(x).getTxtName());
                checklist_title.setTag(listsMerge.get(position).getChildLists().get(x).getTxtID());

                checklist_com.setTag(Integer.toString(z - 1));
                checklist_com.setText(listsMerge.get(position).getChildLists().get(x).getTxtComment());

                if (listsMerge.get(position).getChildLists().get(x).getCheck().equalsIgnoreCase("yes")) {
                    checked_image.setImageResource(R.drawable.check_64);
                    checked_image.setTag("Clicked");
                    container.setVisibility(View.VISIBLE);

                } else {
                    checked_image.setImageResource(R.drawable.box_64);
                    checked_image.setTag("NotClicked");
                    container.setVisibility(View.GONE);

                }

                checked_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checked_image.getTag().equals("NotClicked")) {
                            checked_image.setImageResource(R.drawable.check_64);
                            checked_image.setTag("Clicked");

                            listsMerge.get(position).getChildLists().get(Integer.parseInt(checklist_com.getTag().toString())).setCheck("yes");

                            container.setVisibility(View.VISIBLE);

                        } else if (checked_image.getTag().equals("Clicked")) {
                            checked_image.setImageResource(R.drawable.box_64);
                            checked_image.setTag("NotClicked");

                            listsMerge.get(position).getChildLists().get(Integer.parseInt(checklist_com.getTag().toString())).setCheck("no");

                            container.setVisibility(View.GONE);
                        }
                    }
                });

                checklist_com.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        Log.e("vh.txtGivenName " + position, arg0.toString());

                        if (!arg0.toString().equals("")) {
                            listsMerge.get(position).getChildLists().get(Integer.parseInt(checklist_com.getTag().toString())).setTxtComment(arg0.toString().trim());
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

                vh.hasChildLayout.addView(childView);

            }

            vh.hasChildLayout.setVisibility(View.VISIBLE);

        } else {
            vh.hasChildLayout.setVisibility(View.GONE);
        }

        //i = obj.getData().getContent().get(position).getItemid();
        //t = obj.getData().getContent().get(position).getComment();
        //c = obj.getData().getContent().get(position).getCheck();

        //vh.checklist_title.setText("(Item Name) " + i);
        //vh.checklist_title.setTag(i);
        //vh.checklist_cont.setText(t);

        /*if (c.equalsIgnoreCase("yes")) {
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

        });*/

        return view;
    }

    public ArrayList<MergeList> checklistObj() {
        return listsMerge;
    }
}

