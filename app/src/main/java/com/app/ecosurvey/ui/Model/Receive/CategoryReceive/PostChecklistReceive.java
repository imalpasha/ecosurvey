package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import java.util.List;

public class PostChecklistReceive {

    private String message;
    private String apiStatus;
    private String status;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        private String id;
        List<ChecklistContent> content;

        public List<ChecklistContent> getContent() {
            return content;
        }

        public void setContent(List<ChecklistContent> content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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


    public PostChecklistReceive() {

    }

    public PostChecklistReceive(PostChecklistReceive data) {
        this.status = data.getStatus();
        this.apiStatus = data.getApiStatus();
        this.data = data.getData();
    }
}

