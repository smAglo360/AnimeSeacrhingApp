package com.example.movies.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapters.AnimeAdapter;
import com.example.movies.Adapters.ScrollAdapter;
import com.example.movies.POJO.Anime;
import com.example.movies.R;
import com.example.movies.ViewModels.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerViewAnimes;
    private AnimeAdapter animeAdapter;
    private ScrollAdapter scrollAdapter;
    private ConstraintLayout constraintLayoutSearchingBar;
    private EditText editTextSearchingAnime;
    private TextView textViewNoSearchingResult;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAdapters();
        setAdapters();
        recyclerViewAnimes.setLayoutManager(new GridLayoutManager(this, 2));
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAnimes().observe(this, new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                animeAdapter.setAnimes(anime);
            }
        });
        animeAdapter.setOnReachedEndOfPageListener(new AnimeAdapter.onReachedEndOfPageListener() {
            @Override
            public void onReachedEndOfPage() {
                mainActivityViewModel.loadAnime();
            }
        });
        animeAdapter.setOnAnimeClickListener(new AnimeAdapter.onAnimeClickListener() {
            @Override
            public void onAnimeClicked(Anime anime) {
                Intent intent = DetailsActivity.newIntent(MainActivity.this, anime);
                startActivity(intent);
            }
        });
        editTextSearchingAnime.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String searchingRequest = editTextSearchingAnime.getText().toString();
                    mainActivityViewModel.loadAnimeBySearchingRequest(searchingRequest);
                }
                return false;
            }
        });
        mainActivityViewModel.getIsNoSearchingResult().observe(
                this,
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean searchingResult) {
                        if (searchingResult) {
                            recyclerViewAnimes.setVisibility(View.GONE);
                            textViewNoSearchingResult.setVisibility(View.VISIBLE);
                        } else {
                            recyclerViewAnimes.setVisibility(View.VISIBLE);
                            textViewNoSearchingResult.setVisibility(View.GONE);
                        }
                    }
                });
        scrollAdapter.getIsScrolling().observe(this, new Observer<Boolean>() {
            @SuppressLint("Recycle")
            @Override
            public void onChanged(Boolean isScrolling) {

                if (isScrolling) {
                    TransitionManager.beginDelayedTransition(constraintLayoutSearchingBar,
                            new TransitionSet()
                                    .addTransition(new Slide(Gravity.TOP))
                                    .setDuration(400)
                    );
                    constraintLayoutSearchingBar.setVisibility(View.INVISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(constraintLayoutSearchingBar,
                            new TransitionSet()
                                    .addTransition(new Slide(Gravity.TOP))
                                    .setDuration(300)
                    );
                    constraintLayoutSearchingBar.setVisibility(View.VISIBLE);
                }
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favourite:
                                Intent intent = FavouriteAnime.newIntent(MainActivity.this);
                                startActivity(intent);
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void setAdapters() {
        recyclerViewAnimes.setAdapter(animeAdapter);
        recyclerViewAnimes.addOnScrollListener(scrollAdapter);
    }

    private void initAdapters() {
        animeAdapter = new AnimeAdapter();
        scrollAdapter = new ScrollAdapter();
    }

    private void initViews() {
        constraintLayoutSearchingBar = findViewById(R.id.constraintLayoutSearchingBar);
        recyclerViewAnimes = findViewById(R.id.recycleViewAnimes);
        editTextSearchingAnime = findViewById(R.id.editTextSearchingAnime);
        textViewNoSearchingResult = findViewById(R.id.textViewNoSearchingResult);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
    }


}