package com.example.chenlian.staggeredrcl.model;

/**
 * Created by cl on 2018/5/3.
 */

public class SplashData {
    public String text;
    public String img;

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }

    public String getUrl() {
        return img;
    }

    public void setUrl(String url) {
        this.img = url;
    }
}