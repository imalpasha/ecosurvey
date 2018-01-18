package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class CategoryReceive {

    private String apiStatus;
    private String message;

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


    public CategoryReceive() {

    }

    public CategoryReceive(CategoryReceive data) {

        this.message = data.getMessage();
        this.apiStatus = data.getApiStatus();

    }
}
