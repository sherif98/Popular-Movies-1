package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nanodegree.udacity.android.popularmovies.R;

import fragments.DetailMovieViewPagerFragment;
import logic.TheMovieDatabaseAPI;

public class DisplayDetailMovieActivity extends AppCompatActivity {
    private static final String MOVIE_ID = "movie_id";
    private static final String FAVORITE_FLAG = "favorite";
    private static final String REQUEST_TYPE = "request_type";

    public static Intent newIntent(Context context, int movieId, boolean isFavorite,
                                   TheMovieDatabaseAPI.Request request) {
        Intent intent = new Intent(context, DisplayDetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        intent.putExtra(FAVORITE_FLAG, isFavorite);
        intent.putExtra(REQUEST_TYPE, request.toString());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_movie);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.movie_detail_fragment_container);
        if (fragment == null) {
            fragment = DetailMovieViewPagerFragment.newInstance(getMovieId(), getFavoriteFlag(),
                    getRequestType());
            fragmentManager.beginTransaction().add(R.id.movie_detail_fragment_container,
                    fragment).commit();
        }
    }

    private boolean getFavoriteFlag() {
        Intent intent = getIntent();
        return intent.getBooleanExtra(FAVORITE_FLAG, false);
    }

    private int getMovieId() {
        Intent intent = getIntent();
        return intent.getIntExtra(MOVIE_ID, -1);
    }

    private TheMovieDatabaseAPI.Request getRequestType() {
        Intent intent = getIntent();
        return TheMovieDatabaseAPI.Request.valueOf(intent.getStringExtra(REQUEST_TYPE));
    }
}
