package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;
import com.app.ecosurvey.ui.Activity.survey.SurveyPhotoFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static com.app.ecosurvey.ui.Activity.survey.SurveyPhotoFragment.change;

/**
 * Created by Dell on 10/26/2017.
 */


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<SelectedImagePath> arrayPromo;
    private ArrayList<String> viewItemList = new ArrayList<String>();
    SurveyPhotoFragment frag;

    public ImageListAdapter(SurveyPhotoFragment frag, ArrayList<SelectedImagePath> data, Activity act) {
        arrayPromo = data;
        activity = act;
        this.frag = frag;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.selected_image_view, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int h) {

        holder.actionRemove.setTag(Integer.toString(h));

        File f = new File(arrayPromo.get(h).getImagePath());
        Picasso.with(activity)
                .load(f)
                //.resize(330, 200)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.selectedImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso", "Fetch image");
                        holder.imageLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                        //get as file
                        final String randomImageName = UUID.randomUUID().toString() + "_image.jpeg";
                        //Picasso.with(activity).load(arrayPromo.get(h).getImagePath()).into(picassoImageTarget(activity, "imageDir", randomImageName, holder.selectedImage));

                        holder.imageLoading.setVisibility(View.GONE);

                        //save path to arraylist


                        //Try again online if cache failed
                        Picasso.with(activity)
                                .load(arrayPromo.get(h).getImagePath())
                                .into(holder.selectedImage, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.v("Picasso", "Fetch image");
                                        holder.imageLoading.setVisibility(View.GONE);
                                        getImageFileFromBitmap(activity, "imageDir", randomImageName, holder.selectedImage);
                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });


    }


    @Override
    public int getItemCount() {
        return arrayPromo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView actionRemove;
        final TextView actionChange;
        final ImageView selectedImage;
        final ProgressBar imageLoading;


        public MyViewHolder(View insideMeal) {
            super(insideMeal);

            actionRemove = (TextView) insideMeal.findViewById(R.id.txtActionRemove);
            actionChange = (TextView) insideMeal.findViewById(R.id.txtActionChange);
            selectedImage = (ImageView) insideMeal.findViewById(R.id.selectedImage);
            imageLoading = (ProgressBar) insideMeal.findViewById(R.id.imageLoading);

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

    public void retrieveNewObject(Integer position, ArrayList<SelectedImagePath> data) {
        arrayPromo = data;

        if (position != null)
            notifyItemChanged(position);
        else
            notifyDataSetChanged();

    }

    public void changeItem(String pos) {
        frag.reselectImage(Integer.parseInt(pos));
    }

    public void removeItem(String pos) {

        int position = Integer.parseInt(pos);

        arrayPromo.remove(position);
        //recycler.removeViewAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayPromo.size());
        change = true;
        if (arrayPromo.size() == 0) {
            frag.enablePhotoSelection();
        }

    }

    public void getImageFileFromBitmap(Context context, String imageDir, String imageName, ImageView v) {

        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);

        final File myImageFile = new File(directory, imageName); // Create image file
        FileOutputStream fos = null;

        Bitmap bitmap = ((BitmapDrawable) v.getDrawable()).getBitmap();

        try {
            fos = new FileOutputStream(myImageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        frag.setImagePathForHttp(myImageFile.getAbsolutePath());


    }

    private Target picassoImageTarget(Context context, final String imageDir, final String imageName, final ImageView v) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //load image back

                        Handler uiHandler = new Handler(Looper.getMainLooper());
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ContextWrapper cw = new ContextWrapper(activity);
                                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                                Picasso.with(activity).load(myImageFile).into(v);
                                Log.e("myImageFile", myImageFile.toString());
                            }
                        });

                        Log.i("myImageFile", "image saved to >>>" + myImageFile.getAbsolutePath());
                        frag.setImagePathForHttp(myImageFile.getAbsolutePath());
                    }
                }).start();

                //load image back
                /*ContextWrapper cw = new ContextWrapper(activity);
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File myImageFile = new File(directory, imageName);
                Picasso.with(activity).load(myImageFile).into(v);*/

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }
}