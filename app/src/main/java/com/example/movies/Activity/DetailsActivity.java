package com.example.movies.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movies.POJO.Anime;
import com.example.movies.R;
import com.example.movies.ViewModels.DetailActivityViewModel;
import com.google.android.material.button.MaterialButton;

public class DetailsActivity extends AppCompatActivity {

    private final static String EXTRA_ID = "id";

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private DetailActivityViewModel detailActivityViewModel;
    private ImageView imageViewFavouriteAnimeButton;
    private MaterialButton materialButtonFullInfo;

    private Anime anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);
        initViews();
        detailActivityViewModel = new ViewModelProvider(this)
                .get(DetailActivityViewModel.class);
        anime = (Anime) getIntent().getSerializableExtra(EXTRA_ID);
        Glide.with(this)
                .load(anime.getImages().getJpg().getLarge_jpg_url())
                .into(imageViewPoster);
        textViewTitle.setText(anime.getTitle());
        if (anime.getYear() == null) {
            textViewYear.setText(String.format(
                    getString(R.string.year_of_creation),
                    getString(R.string.unknown))
            );
        } else {
            textViewYear.setText(String.format(
                    getString(R.string.year_of_creation),
                    anime.getYear().toString())
            );
        }
        textViewDescription.setText(anime.getSynopsis());
        materialButtonFullInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(anime.getAnime_url()));
                startActivity(intent);
            }
        });
        Drawable starOn = ContextCompat.getDrawable(this, R.drawable.star_on);
        Drawable starOff = ContextCompat.getDrawable(this, R.drawable.star_off);
        imageViewFavouriteAnimeButton.setImageDrawable(starOff);
        detailActivityViewModel.getFavouriteAnime(anime.getId()).observe(
                this,
                new Observer<Anime>() {
                    @Override
                    public void onChanged(Anime animeFromDb) {
                        if (animeFromDb == null) {
                            imageViewFavouriteAnimeButton.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            imageViewFavouriteAnimeButton.setImageDrawable(starOn);
                                            detailActivityViewModel.insertAnime(anime);
                                        }
                                    });
                        } else {
                            imageViewFavouriteAnimeButton.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            imageViewFavouriteAnimeButton.setImageDrawable(starOff);
                                            detailActivityViewModel.deleteAnime(anime);
                                        }
                                    });
                        }
                    }
                });
        Log.d("DetailsActivity", String.valueOf(anime.getId()));
    }


    public static Intent newIntent(Context context, Anime anime) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_ID, anime);
        return intent;
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        imageViewFavouriteAnimeButton = findViewById(R.id.imageViewFavouriteAnimeButton);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        materialButtonFullInfo = findViewById(R.id.materialButtonFullInfo);
    }
}