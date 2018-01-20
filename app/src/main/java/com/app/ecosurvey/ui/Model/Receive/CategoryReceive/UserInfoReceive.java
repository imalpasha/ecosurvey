package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;

import io.realm.RealmObject;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class UserInfoReceive {

    public UserInfoReceive(UserInfoReceive data) {
        message = data.getMessage();
        this.apiStatus = data.getApiStatus();
        this.data = data.getData();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message;
    }

    private String message;
    private String apiStatus;


    public UserInfoReceive.Data getData() {
        return data;
    }

    public void setData(UserInfoReceive.Data data) {
        data = data;
    }

    private Data data;

    public class Data {

        private String name;
        private String email;
        private String phoneno;
        private String rolename;
        private String stateid;
        private String state;
        private String parlimenCode;
        private String parlimen;
        private String duncode;
        private String dun;
        private String role;
        private String pdmcode;
        private String pdm;


        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getStateid() {
            return stateid;
        }

        public void setStateid(String stateid) {
            this.stateid = stateid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getParlimenCode() {
            return parlimenCode;
        }

        public void setParlimenCode(String parlimenCode) {
            this.parlimenCode = parlimenCode;
        }

        public String getParlimen() {
            return parlimen;
        }

        public void setParlimen(String parlimen) {
            this.parlimen = parlimen;
        }

        public String getDuncode() {
            return duncode;
        }

        public void setDuncode(String duncode) {
            this.duncode = duncode;
        }

        public String getDun() {
            return dun;
        }

        public void setDun(String dun) {
            this.dun = dun;
        }

        public String getPdmcode() {
            return pdmcode;
        }

        public void setPdmcode(String pdmcode) {
            this.pdmcode = pdmcode;
        }

        public String getPdm() {
            return pdm;
        }

        public void setPdm(String pdm) {
            this.pdm = pdm;
        }

    }


    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    /*Initiate Class*/
    public UserInfoReceive() {

    }


}
