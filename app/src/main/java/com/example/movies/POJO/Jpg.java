package com.example.movies.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Jpg implements Serializable {

    @SerializedName("image_url")
    private final String url;

    @Override
    public String toString() {
        return "Jpg{" +
                "url='" + url + '\'' +
                '}';
    }

    public Jpg(String url, String large_jpg_url) {
        this.url = url;
        this.large_jpg_url = large_jpg_url;
    }

    @SerializedName("large_image_url")
    private final String large_jpg_url;

    public String getLarge_jpg_url() {
        return large_jpg_url;
    }

    public String getUrl() {
        return url;
    }
}
