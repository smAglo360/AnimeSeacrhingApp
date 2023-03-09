package com.example.movies.API;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("anime?")
    Single<AnimeResponse> loadAnimes(@Query("page") int page);

    @GET("anime?")
    Single<AnimeResponse> loadAnimeBySearchingRequest(@Query("letter") String searchingRequest);

}
