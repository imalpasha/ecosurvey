package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by imalpasha on 16/01/2018.
 */

public class TokenReceive {

    private String apiStatus;
    private String token;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenReceive() {

    }

    public TokenReceive(TokenReceive data) {

        this.token = data.getToken();
        this.apiStatus = data.getApiStatus();

    }
}
