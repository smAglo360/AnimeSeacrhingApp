package com.example.movies.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.API.AnimeResponse;
import com.example.movies.API.ApiFactory;
import com.example.movies.POJO.Anime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    private final static String TAG = "MainActivityViewModel";

    private final MutableLiveData<List<Anime>> animes = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Boolean> isNoSearchingResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDataLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsDataLoading() {
        return isDataLoading;
    }

    private int page = 1;

    public LiveData<Boolean> getIsNoSearchingResult() {
        return isNoSearchingResult;
    }

    public LiveData<List<Anime>> getAnimes() {
        return animes;
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        loadAnime();
    }

    public void loadAnimeBySearchingRequest(String searchingRequest) {
        Disposable disposable = ApiFactory.apiService.loadAnimeBySearchingRequest(
                        searchingRequest)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isNoSearchingResult.setValue(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AnimeResponse>() {
                    @Override
                    public void accept(AnimeResponse animeResponse) throws Throwable {
                        if (animeResponse.getAnimes().isEmpty()) {
                            isNoSearchingResult.setValue(true);
                            animes.setValue(new ArrayList<>());
                        } else {
                            animes.setValue(animeResponse.getAnimes());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadAnime() {
        Boolean isLoading = isDataLoading.getValue();
        if (isLoading && isLoading != null) {
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadAnimes(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isDataLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isDataLoading.setValue(false);
                    }
                })
                .map(new Function<AnimeResponse, List<Anime>>() {
                    @Override
                    public List<Anime> apply(AnimeResponse animeResponse) throws Throwable {
                        return animeResponse.getAnimes();
                    }
                })
                .subscribe(new Consumer<List<Anime>>() {
                    @Override
                    public void accept(List<Anime> animeList) throws Throwable {
                        List<Anime> loadedAnimes = animes.getValue();
                        if (loadedAnimes != null) {
                            loadedAnimes.addAll(animeList);
                            animes.setValue(loadedAnimes);
                        } else {
                            animes.setValue(animeList);
                        }
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
