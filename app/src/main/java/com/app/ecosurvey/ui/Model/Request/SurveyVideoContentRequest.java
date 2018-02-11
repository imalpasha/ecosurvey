package com.app.ecosurvey.ui.Model.Request;

/**
 * Created by imalpasha on 11/02/2018.
 */

public class SurveyVideoContentRequest {

    private String locationCode;
    private String locationName;
    private String icnumber;

    public String getIcnumber() {
        return icnumber;
    }

    public void setIcnumber(String icnumber) {
        this.icnumber = icnumber;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
