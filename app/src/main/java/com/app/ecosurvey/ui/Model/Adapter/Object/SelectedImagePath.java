package com.app.ecosurvey.ui.Model.Adapter.Object;

import io.realm.RealmObject;

public class SelectedImagePath extends RealmObject{

    private String imagePath;
    private String randomPathCode;
    private String imageString;

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRandomPathCode() {
        return randomPathCode;
    }

    public void setRandomPathCode(String randomPathCode) {
        this.randomPathCode = randomPathCode;
    }

}
