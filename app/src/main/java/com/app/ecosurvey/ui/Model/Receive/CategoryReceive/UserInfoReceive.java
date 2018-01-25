package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import com.app.ecosurvey.ui.Model.Request.ecosurvey.UserInfoRequest;

import java.util.List;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class UserInfoReceive {

    private String message;
    private String apiStatus;
    private Data data;

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

    public UserInfoReceive.Data getData() {
        return data;
    }

    public void setData(UserInfoReceive.Data data) {
        data = data;
    }

    public class Data {

/*<<<<<<< HEAD
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
        private String Role;
        private String pdmcode;
        private String pdm;


        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            this.Role = role;
=======*/
        private String Name;
        private String Email;
        private String Phoneno;
        private String Rolename;
        private String Stateid;
        private String State;
        private String ParlimenCode;
        private String Parlimen;
        private String Duncode;
        private String Dun;
        private String Role;
        private String Pdmcode;
        private String Pdm;

        public List<Location> getLocations() {
            return Locations;
        }

        public void setLocations(List<Location> locations) {
            Locations = locations;
        }

        private List<Location> Locations;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPhoneno() {
            return Phoneno;
        }

        public void setPhoneno(String phoneno) {
            Phoneno = phoneno;
        }

        public String getRolename() {
            return Rolename;
        }

        public void setRolename(String rolename) {
            Rolename = rolename;
        }

        public String getStateid() {
            return Stateid;
        }

        public void setStateid(String stateid) {
            Stateid = stateid;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getParlimenCode() {
            return ParlimenCode;
        }

        public void setParlimenCode(String parlimenCode) {
            ParlimenCode = parlimenCode;
        }

        public String getParlimen() {
            return Parlimen;
        }

        public void setParlimen(String parlimen) {
            Parlimen = parlimen;
        }

        public String getDuncode() {
            return Duncode;
        }

        public void setDuncode(String duncode) {
            Duncode = duncode;
        }

        public String getDun() {
            return Dun;
        }

        public void setDun(String dun) {
            Dun = dun;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public String getPdmcode() {
            return Pdmcode;
        }

        public void setPdmcode(String pdmcode) {
            Pdmcode = pdmcode;
        }

        public String getPdm() {
            return Pdm;
        }

        public void setPdm(String pdm) {
            Pdm = pdm;
        }

    }

    public class Location{

        String Parlimen;
        String ParlimenCode;

        public String getParlimen() {
            return Parlimen;
        }

        public void setParlimen(String parlimen) {
            Parlimen = parlimen;
        }

        public String getParlimenCode() {
            return ParlimenCode;
        }

        public void setParlimenCode(String parlimenCode) {
            ParlimenCode = parlimenCode;
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
