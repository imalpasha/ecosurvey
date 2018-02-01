package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class PostSurveyReceive {

    private String message;
    private String apiStatus;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


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

    public void setMessage(String message) {
        this.message = message;
    }


    public PostSurveyReceive() {

    }

    public PostSurveyReceive(PostSurveyReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.id = data.getId();
    }
}
