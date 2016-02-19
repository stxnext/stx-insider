package com.stxnext.stxinsider.model;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class SliderItem {

    String header;
    String imagePath;
    String description;
    Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SliderItem header(String header) {
        this.header = header;
        return this;
    }

    public SliderItem imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public SliderItem description(String description) {
        this.description = description;
        return this;
    }


    public SliderItem category(Category category) {
        this.category = category;
        return this;
    }
}
