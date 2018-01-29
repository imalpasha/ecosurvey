package com.app.ecosurvey.ui.Model.Request.ecosurvey;

import java.util.List;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class PostSurveyRequest {

    private String IcNumber;
    private String locationCode;
    private String locationName;
    private String locationType;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    private List<Content> content;
    private String token;

    public String getIcNumber() {
        return IcNumber;
    }

    public void setIcNumber(String icNumber) {
        IcNumber = icNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }



    /*Initiate Class*/
    public PostSurveyRequest() {

    }

    public PostSurveyRequest(PostSurveyRequest data) {

        IcNumber = data.getIcNumber();
        locationCode = data.getLocationCode();
        locationName = data.getLocationName();
        locationType = data.getLocationType();
        token = data.getToken();
        content = data.getContent();

    }
}
