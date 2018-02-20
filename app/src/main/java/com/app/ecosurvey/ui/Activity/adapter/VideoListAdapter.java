package com.app.ecosurvey.ui.Activity.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.ecosurvey.R;
import com.app.ecosurvey.ui.Activity.survey.SurveyVideoFragment;
import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedVideoPath;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

import static com.app.ecosurvey.ui.Activity.survey.SurveyVideoFragment.videoChange;

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


        //File f = new File(arrayPromo.get(h).getVideoPath());

        final String f = arrayPromo.get(h).getVideoPath();

        if (f.contains("http")) {
            //try {
            //holder.txtVideoPath.setImageBitmap(retrieveThumbnail(f));
            holder.videoView.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse(f);
            holder.videoView.setVideoURI(videoUri);

            //final String randomVideoName = UUID.randomUUID().toString();

            /*Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        saveInInternalStorage(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();*/

            //saveInInternalStorage(f);
            //downloadFile(f, randomVideoName);

            //final String randomImageName = UUID.randomUUID().toString() + "_image.jpeg";
            //getImageFileFromBitmap(activity, "imageDir", randomImageName, holder.videoView);
            //"https://www.demonuts.com/Demonuts/smallvideo.mp4"
                /*MediaController mediaController = new MediaController(activity);
                mediaController.setAnchorView(holder.videoView);
                holder.videoView.setMediaController(mediaController);
                holder.videoView.setVideoURI(Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4"));*/
            //} catch (Throwable e) {
            // holder.videoView.setVisibility(View.GONE);
            //   Log.e("message123", e.getMessage());
            // }

        } else {
            //Bitmap bMap = ThumbnailUtils.createVideoThumbnail(f, MediaStore.Video.Thumbnails.MINI_KIND);
            //holder.txtVideoPath.setImageBitmap(bMap);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(f);

            //final String randomImageName = UUID.randomUUID().toString() + "_image.jpeg";
            //getImageFileFromBitmap(activity, "imageDir", randomImageName, holder.videoView);

        }

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.start();
                holder.play.setVisibility(View.GONE);
                holder.pause.setVisibility(View.VISIBLE);
            }
        });

        holder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.pause();
                holder.play.setVisibility(View.VISIBLE);
                holder.pause.setVisibility(View.GONE);
            }
        });


        //holder.selectedImage.setVideoURI(Uri.parse(f));
        //holder.selectedImage.start();

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

        holder.actionRemove.setTag(Integer.toString(h));

    }


    @Override
    public int getItemCount() {
        return arrayPromo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView actionRemove;
        final TextView actionChange;
        final ImageView txtVideoPath;
        final VideoView videoView;
        final ImageView play;
        final ImageView pause;


        public MyViewHolder(View insideMeal) {
            super(insideMeal);

            actionRemove = (TextView) insideMeal.findViewById(R.id.txtActionRemove);
            txtVideoPath = (ImageView) insideMeal.findViewById(R.id.txtVideoPath);
            actionChange = (TextView) insideMeal.findViewById(R.id.txtActionChange);
            videoView = (VideoView) insideMeal.findViewById(R.id.videoView);
            play = (ImageView) insideMeal.findViewById(R.id.play);
            pause = (ImageView) insideMeal.findViewById(R.id.pause);

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
        Log.e("remove_From_list", pos);

        arrayPromo.remove(position);

        //recycler.removeViewAt(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayPromo.size());
        //change = true;


        frag.informTheMainList(position);
        if (arrayPromo.size() == 0) {
            frag.enableVideoSelection();
        }
    }

    public ArrayList<String> getRemoveItem() {
        return viewItemList;
    }

    public static Bitmap retrieveThumbnail(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }


    private File saveInInternalStorage(String video) throws IOException {

        Uri videoUri = Uri.parse(video);

        Bitmap finalBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), videoUri);
        File file = null;
        FileOutputStream outputStream;
        try {

            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            file = new File(activity.getCacheDir(), "MyCache" + n);

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("AbsolutePath", file.getAbsolutePath());
        frag.setVideoPathForHttp(file.getAbsolutePath());

        return file;
    }

    /*private class ProgressBack extends AsyncTask<String, String, String> {
        ProgressDialog PD;

        @Override
        protected void onPreExecute() {
            PD = ProgressDialog.show(activity, null, "Please Wait ...", true);
            PD.setCancelable(true);
        }

        @Override
        protected void doInBackground(String... arg0) {
            downloadFile("http://beta-vidizmo.com/hilton.mp4", "Sample.mp4");

        }

        protected void onPostExecute(Boolean result) {
            PD.dismiss();

        }

    }*/

    private void downloadFile(String fileURL, String fileName) {
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "Video";
            File rootFile = new File(rootDir);
            rootFile.mkdir();

            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            FileOutputStream f = new FileOutputStream(new File(rootFile, fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();

            Log.e("rootFilerootFile", rootFile.getAbsolutePath());

        } catch (IOException e) {
            Log.d("Error....", e.toString());
        }

    }
    /*public void getImageFileFromBitmap(Context context, String imageDir, String imageName, VideoView v) {

        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);

        final File myImageFile = new File(directory, imageName); // Create image file
        FileOutputStream fos = null;

        Bitmap bitmap = ((BitmapDrawable) v.get()).getBitmap();

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

        frag.setVideoPathForHttp(myImageFile.getAbsolutePath());

    }*/
}