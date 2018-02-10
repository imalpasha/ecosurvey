package com.app.ecosurvey.ui.Model.Request;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.ChecklistRequest;

/**
 * Created by imalpasha on 10/02/2018.
 */

public class InitChecklistRequest {

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
    public InitChecklistRequest() {

    }

    public InitChecklistRequest(ChecklistRequest data) {

        this.token = data.getToken();

    }
}
