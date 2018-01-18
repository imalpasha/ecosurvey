package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Adapter.Object.SelectedImagePath;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 13/1/2018.
 */

public class Image extends RealmObject {

    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }








}
