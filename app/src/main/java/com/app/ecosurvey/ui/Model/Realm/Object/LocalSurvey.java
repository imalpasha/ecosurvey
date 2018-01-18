package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by imalpasha on 13/1/2018.
 */

public class LocalSurvey extends RealmObject {

    private String localSurveyID;
    private String surveyCategory;
    private String surveyParliment;
    private String surveyLocalProgress;
    private String surveyIssue;
    private String surveyWishlist;
    private RealmList<Image> imagePath;

    public RealmList<Image> getImagePath() {
        return imagePath;
    }

    public void setImagePath(RealmList<Image> imagePath) {
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
