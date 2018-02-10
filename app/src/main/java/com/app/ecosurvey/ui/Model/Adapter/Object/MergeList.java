package com.app.ecosurvey.ui.Model.Adapter.Object;

import java.util.ArrayList;

/**
 * Created by imalpasha on 10/02/2018.
 */

public class MergeList {

    private String header;
    private String id;
    private ArrayList<ChildList> childLists = new ArrayList<ChildList>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<ChildList> getChildLists() {
        return childLists;
    }

    public void setChildLists(ArrayList<ChildList> childLists) {
        this.childLists = childLists;
    }


}
