package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class CategoryRequest {

    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*Initiate Class*/
    public CategoryRequest() {

    }

    public CategoryRequest(CategoryRequest data) {

        this.token = data.getToken();

    }

}
