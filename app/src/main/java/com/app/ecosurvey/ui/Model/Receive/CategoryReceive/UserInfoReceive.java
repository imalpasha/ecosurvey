package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class UserInfoReceive {

    private String message;
    private String apiStatus;

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

    /*Initiate Class*/
    public UserInfoReceive() {

    }

    public UserInfoReceive(UserInfoReceive data) {
        message = data.getMessage();
        apiStatus = data.getApiStatus();
    }


}
