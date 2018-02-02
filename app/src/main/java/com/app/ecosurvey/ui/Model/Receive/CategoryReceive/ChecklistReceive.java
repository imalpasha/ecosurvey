package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import java.util.List;

public class ChecklistReceive {

    private String message;
    private List<Data> data;
    private String apiStatus;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        private String categoryid;
        private String issue;
        private String wishlist;
        private String id;
        private String comment;
        private String check;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getWishlist() {
            return wishlist;
        }

        public void setWishlist(String wishlist) {
            this.wishlist = wishlist;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
