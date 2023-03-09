package com.example.movies.Database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.POJO.Anime;

@Database(entities = {Anime.class}, version = 1, exportSchema = false)
public abstract class FavouriteAnimeDatabase extends RoomDatabase {

    private static final String DB_NAME = "favourite_anime.db";
    private static FavouriteAnimeDatabase instance;

    public static FavouriteAnimeDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            application,
                            FavouriteAnimeDatabase.class,
                            DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract FavouriteAnimeDao favouriteAnimeDao();
}
