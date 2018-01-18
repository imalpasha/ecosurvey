package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class UserInfoRequest {

    private String userID;
    private String token;
    private String url;

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


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /*Initiate Class*/
    public UserInfoRequest() {

    }

    public UserInfoRequest(UserInfoRequest data) {
        userID = data.getUserID();
        token = data.getToken();
        url = data.getUrl();
    }
}
