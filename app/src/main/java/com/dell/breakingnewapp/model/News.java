package com.dell.breakingnewapp.model;

public class News {
    private String title;
    private String description;
    private String puDate;
    private String link;
    private String image;

    public News(String title, String description, String puDate, String link, String image) {
        this.title = title;
        this.description = description;
        this.puDate = puDate;
        this.link = link;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPuDate() {
        return puDate;
    }

    public void setPuDate(String puDate) {
        this.puDate = puDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public News(){}
}
