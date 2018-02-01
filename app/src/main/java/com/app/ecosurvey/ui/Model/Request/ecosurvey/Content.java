package com.app.ecosurvey.ui.Model.Request.ecosurvey;

/**
 * Created by imalpasha on 28/01/2018.
 */

public class Content {

    public Content[] getContent() {
        return content;
    }

    public void setContent(Content[] content) {
        this.content = content;
    }

    private Content content[];



        private String categoryid;
        private String issue;
        private String wishlist;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getWishlist() {
            return wishlist;
        }

        public void setWishlist(String wishlist) {
            this.wishlist = wishlist;
        }



    /*Initiate Class*/
    public Content() {

    }

    public Content(Content data) {

        content = data.getContent();

    }
}
