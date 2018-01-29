package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import java.util.List;

/**
 * Created by User on 1/26/2018.
 */

public class ChecklistReceive {

    private String message;
    private List<Data> data;
    private String apiStatus;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        private int checklistid;
        private String checklist_text;
        private String progressStatus;
        private String comment;

        public int getChecklistid() {
            return checklistid;
        }

        public void setChecklistid(int checklistid) {
            this.checklistid = checklistid;
        }

        public String getChecklist_text() {
            return checklist_text;
        }

        public void setChecklist_text(String checklist_text) {
            this.checklist_text = checklist_text;
        }

        public String getProgressStatus() {
            return progressStatus;
        }

        public void setProgressStatus(String progressStatus) {
            this.progressStatus = progressStatus;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }


    public ChecklistReceive() {

    }

    public ChecklistReceive(ChecklistReceive data) {

        this.message = data.getMessage();
        this.data = data.getData();
        this.apiStatus = data.getApiStatus();

    }
}
