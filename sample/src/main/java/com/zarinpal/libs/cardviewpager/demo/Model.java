package com.zarinpal.libs.cardviewpager.demo;

/**
 * Model Created by farshid roohi on 12/12/17.
 */

public class Model {
    private String title;
    private int bgColor;

    public Model(String title, int bgColor) {
        this.title = title;
        this.bgColor = bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String getTitle() {
        return title;
    }
}
