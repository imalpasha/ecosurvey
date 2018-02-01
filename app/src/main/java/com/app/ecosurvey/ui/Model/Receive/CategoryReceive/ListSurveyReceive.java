package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.Content;

import java.util.List;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class ListSurveyReceive {

    private String status;
    private String apiStatus;
    private String message;
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{

        private String id;
        private String icnumber;
        private String locationCode;
        private String locationType;
        private Boolean processed;
        private String created_at;
        private String updated_at;

        private List<Content> content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcnumber() {
            return icnumber;
        }

        public void setIcnumber(String icnumber) {
            this.icnumber = icnumber;
        }

        public String getLocationCode() {
            return locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }

        public Boolean getProcessed() {
            return processed;
        }

        public void setProcessed(Boolean processed) {
            this.processed = processed;
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

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public ListSurveyReceive() {

    }

    public ListSurveyReceive(ListSurveyReceive data) {

        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.message = data.getMessage();
        this.data = data.getData();

    }
}
