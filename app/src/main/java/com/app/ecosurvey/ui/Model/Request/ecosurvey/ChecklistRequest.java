package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by User on 1/26/2018.
 */

public class ChecklistRequest {
    String token;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*Initiate Class*/
    public ChecklistRequest() {

    }

    public ChecklistRequest(ChecklistRequest data) {

        this.token = data.getToken();

    }

}
