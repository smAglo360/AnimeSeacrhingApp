package com.example.movies.POJO;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favourite_anime")
public class Anime implements Serializable {

    @PrimaryKey
    @SerializedName("mal_id")
    private final int id;

    public String getSynopsis() {
        return synopsis;
    }

    @SerializedName("year")
    private final Integer year;

    public Anime(int id,
                 Integer year,
                 String anime_url,
                 String title,
                 String synopsis,
                 Images images,
                 double score
    ) {
        this.id = id;
        this.year = year;
        this.anime_url = anime_url;
        this.title = title;
        this.synopsis = synopsis;
        this.images = images;
        this.score = score;
    }

    @SerializedName("url")
    private final String anime_url;

    private final String title;

    public String getAnime_url() {
        return anime_url;
    }

    @SerializedName("synopsis")
    private final String synopsis;

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("images")
    @Embedded
    private final Images images;

    @Override
    public String toString() {
        return "Anime{" +
                "images=" + images +
                ", score=" + score +
                '}';
    }

    @SerializedName("score")
    private final double score;

    public Images getImages() {
        return images;
    }

    public double getScore() {
        return score;
    }

    public int getId() {
        return id;
    }
}
