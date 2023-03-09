package com.example.movies.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.POJO.Anime;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavouriteAnimeDao {

    @Query("SELECT * FROM favourite_anime")
    LiveData<List<Anime>> getAllFavouriteAnimes();

    @Query("SELECT * FROM favourite_anime WHERE id = :id")
    LiveData<Anime> getFavouriteAnime(int id);

    @Insert
    Completable insertFavouriteAnime(Anime anime);

    @Delete
    Completable deleteFavouriteAnime(Anime anime);
}
