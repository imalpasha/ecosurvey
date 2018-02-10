package com.app.ecosurvey.ui.Model.Receive;

import java.util.ArrayList;

/**
 * Created by imalpasha on 10/02/2018.
 */

public class InitChecklistReceive {

    private String status;
    private String apiStatus;
    private String message;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    private ArrayList<Data> data;

    public class Data {

        private String id;
        private String parent_id;
        private String text;
        private String order;
        private Boolean has_child;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public Boolean getHas_child() {
            return has_child;
        }

        public void setHas_child(Boolean has_child) {
            this.has_child = has_child;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public InitChecklistReceive() {

    }

    public InitChecklistReceive(InitChecklistReceive data) {

        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.message = data.getMessage();
        this.data = data.getData();

    }
}
