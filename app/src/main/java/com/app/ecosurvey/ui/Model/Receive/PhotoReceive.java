package com.app.ecosurvey.ui.Model.Receive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imalpasha on 31/01/2018.
 */

public class PhotoReceive {


    private String status;
    private String apiStatus;
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;

    public class Data {

        private String id;
        private String created_at;
        private String updated_at;
        private PhotoContent content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public PhotoContent getContent() {
            return content;
        }

        public void setContent(PhotoContent content) {
            this.content = content;
        }



    }

    public class PhotoContent {

        private ArrayList<String> images;

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messasge) {
        this.message = messasge;
    }


    public PhotoReceive() {

    }

    public PhotoReceive(PhotoReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.message = data.getMessage();
        this.data = data.getData();
    }

}
