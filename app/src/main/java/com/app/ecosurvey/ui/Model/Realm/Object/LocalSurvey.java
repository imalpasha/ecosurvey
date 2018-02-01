package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by imalpasha on 13/1/2018.
 */

public class LocalSurvey extends RealmObject {

    @PrimaryKey
    private String localSurveyID;
    private String surveyCategory;
    private String surveyParliment;
    private String surveyLocalProgress;
    private String surveyIssue;
    private String surveyWishlist;
    private String imagePath;
    private String imageString;
    private String surveyStatus;
    private String statusAPI;
    private String statusCreated;
    private String statusUpdated;
    private String videoPath;
    private String photoUpdateDate;

    public String getPhotoUpdateDate() {
        return photoUpdateDate;
    }

    public void setPhotoUpdateDate(String photoUpdateDate) {
        this.photoUpdateDate = photoUpdateDate;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String  imagePath) {
        this.imagePath = imagePath;
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
