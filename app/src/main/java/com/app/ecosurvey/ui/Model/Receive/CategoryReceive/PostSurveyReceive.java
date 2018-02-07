package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class PostSurveyReceive {

    private String message;
    private String apiStatus;
    private String status;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        private String id;
        private String updated_at;

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

    public void setMessage(String message) {
        this.message = message;
    }


    public PostSurveyReceive() {

    }

    public PostSurveyReceive(PostSurveyReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.data = data.getData();
    }
}
