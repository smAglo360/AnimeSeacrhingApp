package com.example.movies.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.POJO.Anime;
import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private static final String TAG = "AnimeAdapter";
    private List<Anime> animes = new ArrayList<>();

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
        notifyDataSetChanged();
    }

    private onAnimeClickListener onAnimeClickListener;

    public void setOnAnimeClickListener(AnimeAdapter.onAnimeClickListener onAnimeClickListener) {
        this.onAnimeClickListener = onAnimeClickListener;
    }

    private onReachedEndOfPageListener onReachedEndOfPageListener;

    public void setOnReachedEndOfPageListener(
            onReachedEndOfPageListener onReachedEndOfPageListener) {
        this.onReachedEndOfPageListener = onReachedEndOfPageListener;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_item,
                parent,
                false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animes.get(position);
        Glide.with(holder.itemView)
                .load(animes.get(position).getImages().getJpg().getUrl())
                .into(holder.imageViewPoster);
        double score = anime.getScore();
        int backgroundId;
        if (score > 7.0) {
            backgroundId = R.drawable.rating_green_circle;
        } else if (score > 5.0) {
            backgroundId = R.drawable.rating_orange_circle;
        } else {
            backgroundId = R.drawable.rating_red_circle;
        }
        Drawable backgroundColor = ContextCompat.getDrawable(
                holder.itemView.getContext(),
                backgroundId);
        holder.textViewScore.setBackground(backgroundColor);
        String strScore = String.valueOf(score);
        if (strScore.length() > 3) {
            holder.textViewScore.setText(strScore.substring(0, 3));
        } else {
            holder.textViewScore.setText(strScore);
        }
        if (position >= animes.size() - 10 && onReachedEndOfPageListener != null) {
            onReachedEndOfPageListener.onReachedEndOfPage();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAnimeClickListener != null) {
                    onAnimeClickListener.onAnimeClicked(anime);
                }
            }
        });
    }

    public interface onReachedEndOfPageListener {
        void onReachedEndOfPage();
    }

    public interface onAnimeClickListener {
        void onAnimeClicked(Anime anime);
    }

    @Override
    public int getItemCount() {
        return animes.size();
    }

    static class AnimeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewScore;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewScore = itemView.findViewById(R.id.textViewScore);
        }
    }
}
