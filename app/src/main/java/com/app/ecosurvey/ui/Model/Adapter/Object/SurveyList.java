package com.app.ecosurvey.ui.Model.Adapter.Object;

import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 1/19/2018.
 */

public class SurveyList {

    @PrimaryKey
    private String localSurveyID;
    private String surveyCategory;
    private String surveyParliment;
    private String surveyLocalProgress;
    private String surveyIssue;
    private String surveyWishlist;
    private String imageString;
    private String surveyStatus;
    private String statusAPI;
    private String statusCreated;
    private String statusUpdated;

    public String getStatusUpdated() {
        return statusUpdated;
    }

    public void setStatusUpdated(String statusUpdated) {
        this.statusUpdated = statusUpdated;
    }

    public String getStatusAPI() {
        return statusAPI;
    }

    public void setStatusAPI(String statusAPI) {
        this.statusAPI = statusAPI;
    }

    public String getStatusCreated() {
        return statusCreated;
    }

    public void setStatusCreated(String statusCreated) {
        this.statusCreated = statusCreated;
    }

    public String getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(String surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getSurveyWishlist() {
        return surveyWishlist;
    }

    public void setSurveyWishlist(String surveyWishlist) {
        this.surveyWishlist = surveyWishlist;
    }

    public String getSurveyIssue() {
        return surveyIssue;
    }

    public void setSurveyIssue(String surveyIssue) {
        this.surveyIssue = surveyIssue;
    }

    public String getSurveyLocalProgress() {
        return surveyLocalProgress;
    }

    public void setSurveyLocalProgress(String surveyLocalProgress) {
        this.surveyLocalProgress = surveyLocalProgress;
    }

    public String getLocalSurveyID() {
        return localSurveyID;
    }

    public void setLocalSurveyID(String localSurveyID) {
        this.localSurveyID = localSurveyID;
    }

    public String getSurveyCategory() {
        return surveyCategory;
    }

    public void setSurveyCategory(String surveyCategory) {
        this.surveyCategory = surveyCategory;
    }

    public String getSurveyParliment() {
        return surveyParliment;
    }

    public void setSurveyParliment(String surveyParliment) {
        this.surveyParliment = surveyParliment;
    }

}