package com.app.ecosurvey.ui.Model.Realm.Object;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 12/02/2018.
 */

public class SavedChecklistRealm extends RealmObject {

    private String savedChecklist;

    public String getSavedChecklist() {
        return savedChecklist;
    }

    public void setSavedChecklist(String savedChecklist) {
        this.savedChecklist = savedChecklist;
    }

}
