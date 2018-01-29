package com.app.ecosurvey.ui.Model.Adapter.Object;

import android.net.Uri;

public class SelectedVideoPath {

    private String videoPath;
    private String randomPathCode;
    private String videoString;
    private Uri videoPathUri;

    public Uri getVideoPathUri() {
        return videoPathUri;
    }

    public void setVideoPathUri(Uri videoPathUri) {
        this.videoPathUri = videoPathUri;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoString() {
        return videoString;
    }

    public void setVideoString(String videoString) {
        this.videoString = videoString;
    }

    public String getRandomPathCode() {
        return randomPathCode;
    }

    public void setRandomPathCode(String randomPathCode) {
        this.randomPathCode = randomPathCode;
    }

}
