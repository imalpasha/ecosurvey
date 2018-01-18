package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by Dell on 10/9/2016.
 */

public class LoginReceive {

    private String status;
    private String apiStatus;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginReceive() {

    }

    public LoginReceive(LoginReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();

    }

}
