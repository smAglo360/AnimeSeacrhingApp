package com.example.movies.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.Database.FavouriteAnimeDao;
import com.example.movies.Database.FavouriteAnimeDatabase;
import com.example.movies.POJO.Anime;

import java.util.List;

public class FavouriteAnimeViewModel extends AndroidViewModel {

    private FavouriteAnimeDao favouriteAnimeDao;

    public FavouriteAnimeViewModel(@NonNull Application application) {
        super(application);
        favouriteAnimeDao = FavouriteAnimeDatabase.getInstance(application).favouriteAnimeDao();
    }

    public LiveData<List<Anime>> getAllFavouriteAnimes() {
        return favouriteAnimeDao.getAllFavouriteAnimes();
    }
}
