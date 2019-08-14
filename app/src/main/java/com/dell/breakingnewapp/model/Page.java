package com.dell.breakingnewapp.model;

import java.util.regex.Pattern;

public class Page {
    private int image;
    private String namePage;
    private String link;

    public Page(int image, String namePage,String link) {
        this.image = image;
        this.namePage = namePage;
        this.link = link;
    }
    public Page(){
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setNamePage(String namePage) {
        this.namePage = namePage;
    }

    public int getImage() {
        return image;
    }

    public String getNamePage() {
        return namePage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
