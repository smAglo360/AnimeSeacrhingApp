package com.example.movies.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapters.AnimeAdapter;
import com.example.movies.POJO.Anime;
import com.example.movies.R;
import com.example.movies.ViewModels.FavouriteAnimeViewModel;

import java.util.List;

public class FavouriteAnime extends AppCompatActivity {

    private RecyclerView recycleViewFavouriteAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_anime);
        FavouriteAnimeViewModel favouriteAnimeViewModel = new ViewModelProvider(this)
                .get(FavouriteAnimeViewModel.class);
        AnimeAdapter animeAdapter = new AnimeAdapter();
        initViews();
        recycleViewFavouriteAnime.setAdapter(animeAdapter);
        recycleViewFavouriteAnime.setLayoutManager(new GridLayoutManager(this, 2));
        favouriteAnimeViewModel.getAllFavouriteAnimes().observe(
                this,
                new Observer<List<Anime>>() {
                    @Override
                    public void onChanged(List<Anime> anime) {
                        animeAdapter.setAnimes(anime);
                    }
                });
        animeAdapter.setOnAnimeClickListener(new AnimeAdapter.onAnimeClickListener() {
            @Override
            public void onAnimeClicked(Anime anime) {
                Intent intent = DetailsActivity.newIntent(FavouriteAnime.this, anime);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        recycleViewFavouriteAnime = findViewById(R.id.recycleViewFavouriteAnime);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteAnime.class);
    }
}