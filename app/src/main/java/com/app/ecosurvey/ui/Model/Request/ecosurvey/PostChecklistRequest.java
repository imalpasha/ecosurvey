package com.app.ecosurvey.ui.Model.Request.ecosurvey;

import java.util.HashMap;

public class PostChecklistRequest {

    private String IcNumber;
    private String locationCode;
    private String locationName;
    private String locationType;
    private String token;
    private String id;
    private String content;

    HashMap<String, Object> dicmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<String, Object> getDicmap() {
        return dicmap;
    }

    public void setDicmap(HashMap<String, Object> dicmap) {
        this.dicmap = dicmap;
    }

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

    public PostChecklistRequest() {

    }

    public PostChecklistRequest(PostChecklistRequest data) {

        IcNumber = data.getIcNumber();
        locationCode = data.getLocationCode();
        locationName = data.getLocationName();
        locationType = data.getLocationType();
        token = data.getToken();
        dicmap = data.getDicmap();
        content = data.getContent();
        id = data.getId();

    }
}

