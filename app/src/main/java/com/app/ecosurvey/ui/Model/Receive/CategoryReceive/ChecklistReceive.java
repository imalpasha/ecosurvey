package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import java.util.List;

public class ChecklistReceive {

    private String message;
    private String apiStatus;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {


        private String locationCode;
        private String locationName;
        private String id;
        List<ChecklistContent> content;

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


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public List<ChecklistContent> getContent() {
            return content;
        }

        public void setContent(List<ChecklistContent> content) {
            this.content = content;
        }

    }

    public class ChecklistContent {

        private String itemid;
        private String comment;
        private String check;

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCheck() {
            return check;
        }

        public void setCheck(String check) {
            this.check = check;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }


    public ChecklistReceive() {

    }

    public ChecklistReceive(ChecklistReceive data) {

        this.message = data.getMessage();
        this.data = data.getData();
        this.apiStatus = data.getApiStatus();

    }
}
