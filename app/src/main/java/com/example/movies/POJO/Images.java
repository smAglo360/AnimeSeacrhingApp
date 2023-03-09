package com.example.movies.POJO;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Images implements Serializable {
    @Override
    public String toString() {
        return "Images{" +
                "jpg=" + jpg +
                '}';
    }

    @SerializedName("jpg")
    @Embedded
    private final Jpg jpg;

    public Images(Jpg jpg) {
        this.jpg = jpg;
    }

    public Jpg getJpg() {
        return jpg;
    }
}
