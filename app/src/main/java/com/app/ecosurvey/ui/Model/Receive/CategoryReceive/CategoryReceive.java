package com.app.ecosurvey.ui.Model.Receive.CategoryReceive;

import java.util.List;

/**
 * Created by imalpasha on 17/01/2018.
 */

public class CategoryReceive {

    private String apiStatus;
    private String message;

    public CategoryReceive.Data getData() {
        return data;
    }

    public void setData(CategoryReceive.Data data) {
        data = data;
    }

    private Data data;

    public class Data {

        private List<Items> items;

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

    }

    public class Items {

        private String id;
        private String title;

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

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public CategoryReceive() {

    }

    public CategoryReceive(CategoryReceive data) {

        this.message = data.getMessage();
        this.apiStatus = data.getApiStatus();
        this.data = data.getData();

    }
}
