package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

/**
 * Created by Dell on 10/9/2016.
 */

public class UploadPhotoReceive {

    private String info_desc;
    private String info_name;
    private String status;
    private String message;

    public String getInfo_desc() {
        return info_desc;
    }

    public void setInfo_desc(String info_desc) {
        this.info_desc = info_desc;
    }

    public String getInfo_name() {
        return info_name;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UploadPhotoReceive() {

    }

    public UploadPhotoReceive(UploadPhotoReceive data) {
        this.status = data.getStatus();
        //this.message = data.getMessage();
        this.info_desc = data.getInfo_desc();
        this.info_name = data.getInfo_name();
        this.message = data.getMessage();
    }

}
