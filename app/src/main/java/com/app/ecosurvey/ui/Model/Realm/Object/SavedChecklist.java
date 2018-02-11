package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Adapter.Object.MergeList;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 12/02/2018.
 */

public class SavedChecklist extends RealmObject{

    private ArrayList<MergeList> listsMerge;

    public ArrayList<MergeList> getListsMerge() {
        return listsMerge;
    }

    public void setListsMerge(ArrayList<MergeList> listsMerge) {
        this.listsMerge = listsMerge;
    }

}
