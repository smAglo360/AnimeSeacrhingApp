package com.example.movies.Adapters;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollAdapter extends RecyclerView.OnScrollListener {

    private final MutableLiveData<Boolean> isScrolling = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsScrolling() {
        return isScrolling;
    }

    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                isScrolling.setValue(false);
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                isScrolling.setValue(true);
                break;
        }

    }
}