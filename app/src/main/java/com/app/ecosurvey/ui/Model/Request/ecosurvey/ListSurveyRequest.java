package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class ListSurveyRequest {

    private String token;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*Initiate Class*/
    public ListSurveyRequest() {

    }

    public ListSurveyRequest(ListSurveyRequest data) {

        token = data.getToken();
        url = data.getUrl();

    }
}
