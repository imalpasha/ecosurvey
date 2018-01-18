package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by Dell on 11/4/2015.
 */
public class LogoUploadRequest {

    /*Local Data Send To Server*/
    String logobase64;

    public String getLogobase64() {
        return logobase64;
    }

    public void setLogobase64(String logobase64) {
        this.logobase64 = logobase64;
    }

    /*Initiate Class*/
    public LogoUploadRequest() {

    }

    public LogoUploadRequest(LogoUploadRequest data) {

        logobase64 = data.getLogobase64();

    }

}
