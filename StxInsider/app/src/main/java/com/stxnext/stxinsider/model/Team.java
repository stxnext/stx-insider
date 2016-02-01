package com.stxnext.stxinsider.model;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class Team {

    String header;
    String imagePath;
    String description;
    TeamCategory category;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeamCategory getCategory() {
        return category;
    }

    public void setCategory(TeamCategory category) {
        this.category = category;
    }

    public Team header(String header) {
        this.header = header;
        return this;
    }

    public Team imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Team description(String description) {
        this.description = description;
        return this;
    }


    public Team category(TeamCategory category) {
        this.category = category;
        return this;
    }
}
