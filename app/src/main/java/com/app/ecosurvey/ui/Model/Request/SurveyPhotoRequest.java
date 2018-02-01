package com.app.ecosurvey.ui.Model.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by imalpasha on 30/01/2018.
 */

public class SurveyPhotoRequest {

    private String stringContent;
    private List<MultipartBody.Part> parts;
    private String token;
    HashMap<String, Object> map;

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public List<MultipartBody.Part> getParts() {
        return parts;
    }

    public void setParts(List<MultipartBody.Part> parts) {
        this.parts = parts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
