package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by Dell on 10/9/2016.
 */

public class LoginReceive {

    private String status;
    private String apiStatus;
    private String message;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


        private String token;

        public LoginUser getUser() {
            return user;
        }

        public void setUser(LoginUser user) {
            this.user = user;
        }

        private LoginUser user;
    }


    public class LoginUser {

        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

    }

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
        this.data = data.getData();
    }

}
