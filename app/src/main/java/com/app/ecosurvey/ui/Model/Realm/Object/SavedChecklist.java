package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Adapter.Object.MergeList;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 12/02/2018.
 */

public class SavedChecklist {

    private ArrayList<MergeList> listsMerge;
    private String checklistID;
    private String checklistLocation;
    private String checklistLocationName;
    private String updatedAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getChecklistID() {
        return checklistID;
    }

    public void setChecklistID(String checklistID) {
        this.checklistID = checklistID;
    }

    public String getChecklistLocation() {
        return checklistLocation;
    }

    public void setChecklistLocation(String checklistLocation) {
        this.checklistLocation = checklistLocation;
    }

    public String getChecklistLocationName() {
        return checklistLocationName;
    }

    public void setChecklistLocationName(String checklistLocationName) {
        this.checklistLocationName = checklistLocationName;
    }

    public ArrayList<MergeList> getListsMerge() {
        return listsMerge;
    }

    public void setListsMerge(ArrayList<MergeList> listsMerge) {
        this.listsMerge = listsMerge;
    }

}
