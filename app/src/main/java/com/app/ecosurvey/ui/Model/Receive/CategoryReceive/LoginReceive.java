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

        private String token;
        private LoginUser user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public LoginUser getUser() {
            return user;
        }

        public void setUser(LoginUser user) {
            this.user = user;
        }

    }


    public class LoginUser {

        private String Role;

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            this.Role = role;
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
