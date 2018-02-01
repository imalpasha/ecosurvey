package com.app.ecosurvey.ui.Model.Request;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.CategoryRequest;

/**
 * Created by imalpasha on 31/01/2018.
 */

public class VideoRequest {

    private String token;
    private String url;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*Initiate Class*/
    public VideoRequest() {

    }

    public VideoRequest(PhotoRequest data) {

        this.token = data.getToken();
        this.url = data.getUrl();

    }
}
