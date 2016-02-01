package com.stxnext.stxinsider.model;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryHeader {
    String header;
    String imagePath;
    String footer;
    TeamCategory category;
    String background;

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

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public TeamCategory getCategory() {
        return category;
    }

    public void setCategory(TeamCategory category) {
        this.category = category;
    }

    public TeamCategoryHeader header(String header) {
        this.header = header;
        return this;
    }

    public TeamCategoryHeader imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public TeamCategoryHeader footer(String footer) {
        this.footer = footer;
        return this;
    }

    public TeamCategoryHeader category(TeamCategory category) {
        this.category = category;
        return this;
    }

    public TeamCategoryHeader background(String background) {
        this.background = background;
        return this;
    }
}
