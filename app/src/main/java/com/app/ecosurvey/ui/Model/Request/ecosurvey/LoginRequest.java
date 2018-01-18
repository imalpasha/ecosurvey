package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by Dell on 11/4/2015.
 */
public class LoginRequest {

    private String icnumber;
    private String password;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getIcnumber() {
        return icnumber;
    }

    public void setIcnumber(String icnumber) {
        this.icnumber = icnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*Initiate Class*/
    public LoginRequest() {

    }

    public LoginRequest(LoginRequest data) {
        icnumber = data.getIcnumber();
        password = data.getPassword();
        token = data.getToken();

    }

}
