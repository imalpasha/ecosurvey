package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by imalpasha on 16/01/2018.
 */

public class TokenRequest {

    public String getGetToken() {
        return getToken;
    }

    public void setGetToken(String getToken) {
        this.getToken = getToken;
    }

    /*Local Data Send To Server*/
    String getToken;


    /*Initiate Class*/
    public TokenRequest() {

    }

    public TokenRequest(TokenRequest data) {

        this.getToken = data.getGetToken();

    }
}
