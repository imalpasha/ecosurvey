package com.app.ecosurvey.ui.Model.Realm.Object;

import io.realm.RealmObject;

public class ChecklistCached extends RealmObject {

    private String checkListString;

    public String getCheckListString() {
        return checkListString;
    }

    public void setCheckListString(String checkListString) {
        this.checkListString = checkListString;
    }

}

