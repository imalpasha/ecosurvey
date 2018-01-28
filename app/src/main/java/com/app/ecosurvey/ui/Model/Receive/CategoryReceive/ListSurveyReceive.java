package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

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

        public String getSurveyID() {
            return surveyID;
        }

        public void setSurveyID(String surveyID) {
            this.surveyID = surveyID;
        }

        private String surveyID;
        private String categoryid;
        private String issue;
        private String wishlist;

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
