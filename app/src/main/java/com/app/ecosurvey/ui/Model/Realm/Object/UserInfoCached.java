package com.app.ecosurvey.ui.Model.Realm.Object;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class UserInfoCached extends RealmObject {

    private String userInfoString;

    public String getUserInfoString() {
        return userInfoString;
    }

    public void setUserInfoString(String userInfoString) {
        this.userInfoString = userInfoString;
    }

}
