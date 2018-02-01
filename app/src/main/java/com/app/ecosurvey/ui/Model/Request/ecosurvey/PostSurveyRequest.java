package com.app.ecosurvey.ui.Model.Request.ecosurvey;

import java.util.HashMap;
import java.util.List;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class PostSurveyRequest {

    private String IcNumber;
    private String locationCode;
    private String locationName;
    private String locationType;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    HashMap<String, Object> dicmap;

    public HashMap<String, Object> getDicmap() {
        return dicmap;
    }

    public void setDicmap(HashMap<String, Object> dicmap) {
        this.dicmap = dicmap;
    }

    /*public Content getContent() {
        return content[];
    }

    public void setContent(Content content[]) {
        this.content = content;
    }*/

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
        dicmap = data.getDicmap();
        content = data.getContent();
        id = data.getId();

    }
}
