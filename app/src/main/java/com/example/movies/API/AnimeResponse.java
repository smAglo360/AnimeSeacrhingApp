package com.example.movies.API;

import com.example.movies.POJO.Anime;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeResponse {

    @Override
    public String toString() {
        return "AnimeResponse{" +
                "animes=" + animes +
                '}';
    }

    public AnimeResponse(List<Anime> animes) {
        this.animes = animes;
    }

    public List<Anime> getAnimes() {
        return animes;
    }

    @SerializedName("data")
    private final List<Anime> animes;
}
