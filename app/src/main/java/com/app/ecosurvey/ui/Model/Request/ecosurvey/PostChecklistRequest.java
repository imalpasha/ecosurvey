package com.app.ecosurvey.ui.Model.Request.ecosurvey;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostChecklistRequest {

    private String IcNumber;
    private String locationCode;
    private String locationName;
    private String locationType;
    private String token;
    private String id;
    private String content;

    public List<MultipartBody.Part> getParts() {
        return parts;
    }

    public void setParts(List<MultipartBody.Part> parts) {
        this.parts = parts;
    }

    List<MultipartBody.Part> parts;

    public List<Content> getParts2() {
        return parts2;
    }

    public void setParts2(List<Content> parts2) {
        this.parts2 = parts2;
    }

    List<Content> parts2;

    HashMap<String, RequestBody> map;

    public HashMap<String, Content> getSecondmap() {
        return secondmap;
    }

    public void setSecondmap(HashMap<String, Content> secondmap) {
        this.secondmap = secondmap;
    }

    HashMap<String, Content> secondmap;


    public HashMap<String, RequestBody> getMap() {
        return map;
    }

    public void setMap(HashMap<String, RequestBody> map) {
        this.map = map;
    }


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
        map = data.getMap();
        secondmap = data.getSecondmap();
        parts = data.getParts();

    }
}

