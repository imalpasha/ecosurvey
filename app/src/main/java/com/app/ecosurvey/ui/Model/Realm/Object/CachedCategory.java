package com.app.ecosurvey.ui.Model.Realm.Object;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 19/01/2018.
 */

public class CachedCategory extends RealmObject {

    private String id;
    private String title;
    private String categoryList;

    public String getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String categoryList) {
        this.categoryList = categoryList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
