package com.app.ecosurvey.ui.Model.Request;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by imalpasha on 11/02/2018.
 */

public class SurveyVideoRequest {

    private String stringContent;
    private List<MultipartBody.Part> parts;
    private String token;
    HashMap<String, RequestBody> map;
    public List<MultipartBody.Part> getParts() {
        return parts;
    }

    public HashMap<String, RequestBody> getMap() {
        return map;
    }

    public void setMap(HashMap<String, RequestBody> map) {
        this.map = map;
    }

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
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
