package com.example.movies.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.Database.FavouriteAnimeDao;
import com.example.movies.Database.FavouriteAnimeDatabase;
import com.example.movies.POJO.Anime;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivityViewModel extends AndroidViewModel {

    private final FavouriteAnimeDao favouriteAnimeDao;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailActivityViewModel(@NonNull Application application) {
        super(application);
        favouriteAnimeDao = FavouriteAnimeDatabase.getInstance(application).favouriteAnimeDao();
    }

    public void insertAnime(Anime anime) {
        Disposable disposable = favouriteAnimeDao.insertFavouriteAnime(anime)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("TAG", throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public void deleteAnime(Anime anime) {
        Log.d("TAG", anime.toString());
        Disposable disposable = favouriteAnimeDao.deleteFavouriteAnime(anime)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("TAG", throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<Anime> getFavouriteAnime(int id) {
        return favouriteAnimeDao.getFavouriteAnime(id);
    }

}
