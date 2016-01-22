package com.stxnext.stxinsider.model;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCatergoryHeader {
    String header;
    String imagePath;
    String footer;

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


    public TeamCatergoryHeader header(String header) {
        this.header = header;
        return this;
    }

    public TeamCatergoryHeader imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public TeamCatergoryHeader footer(String footer) {
        this.footer = footer;
        return this;
    }
}
