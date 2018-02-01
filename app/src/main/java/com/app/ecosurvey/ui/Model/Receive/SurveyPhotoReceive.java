package com.app.ecosurvey.ui.Model.Receive;

/**
 * Created by imalpasha on 30/01/2018.
 */

public class SurveyPhotoReceive {

    private String status;
    private String apiStatus;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messasge) {
        this.message = messasge;
    }


    public SurveyPhotoReceive() {

    }

    public SurveyPhotoReceive(SurveyPhotoReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.message = data.getMessage();
    }
}
