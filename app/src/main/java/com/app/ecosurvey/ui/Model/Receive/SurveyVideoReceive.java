package com.app.ecosurvey.ui.Model.Receive;

/**
 * Created by imalpasha on 11/02/2018.
 */

public class SurveyVideoReceive {

    private String status;
    private String apiStatus;
    private String message;

    public SurveyPhotoReceive.Data getData() {
        return data;
    }

    public void setData(SurveyPhotoReceive.Data data) {
        this.data = data;
    }

    private SurveyPhotoReceive.Data data;

    public class Data{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

    }

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


    public SurveyVideoReceive() {

    }

    public SurveyVideoReceive(SurveyVideoReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.message = data.getMessage();
    }

}
