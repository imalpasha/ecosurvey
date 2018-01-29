package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.survey.SurveyVideoFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedVideoPath;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<SelectedVideoPath> arrayPromo;
    private ArrayList<String> viewItemList = new ArrayList<String>();
    SurveyVideoFragment frag;

    public VideoListAdapter(SurveyVideoFragment frag, ArrayList<SelectedVideoPath> data, Activity act) {
        arrayPromo = data;
        activity = act;
        this.frag = frag;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.selected_video_view, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int h) {

        holder.actionRemove.setTag(Integer.toString(h));

        //File f = new File(arrayPromo.get(h).getVideoPath());

        String f = arrayPromo.get(h).getVideoPath();

        holder.selectedImage.setVideoURI(Uri.parse(f));
        holder.selectedImage.start();

        /*Picasso.with(activity)
                .load(f)
                //.resize(330, 200)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.selectedImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso", "Fetch image");
                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(activity)
                                .load(arrayPromo.get(h).getVideoPath())
                                .into(holder.selectedImage, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.v("Picasso", "Fetch image");
                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });*/

    }


    @Override
    public int getItemCount() {
        return arrayPromo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView actionRemove;
        final TextView actionChange;
        final VideoView selectedImage;


        public MyViewHolder(View insideMeal) {
            super(insideMeal);

            actionRemove = (TextView) insideMeal.findViewById(R.id.txtActionRemove);
            actionChange = (TextView) insideMeal.findViewById(R.id.txtActionChange);
            selectedImage = (VideoView) insideMeal.findViewById(R.id.selectedImage);

            actionRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeItem(actionRemove.getTag().toString());
                }
            });

            actionChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeItem(actionRemove.getTag().toString());
                }
            });


        }
    }

    public void retrieveNewObject(Integer position, ArrayList<SelectedVideoPath> data) {
        arrayPromo = data;

        if (position != null)
            notifyItemChanged(position);
        else
            notifyDataSetChanged();

    }

    public void changeItem(String pos) {
        frag.reselectVideo(Integer.parseInt(pos));
    }

    public void removeItem(String pos) {

        int position = Integer.parseInt(pos);

        arrayPromo.remove(position);
        //recycler.removeViewAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayPromo.size());

        if (arrayPromo.size() == 0) {
            frag.enableVideoSelection();
        }
    }
}