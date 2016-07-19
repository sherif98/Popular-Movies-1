package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayDetailMovieActivity extends AppCompatActivity {
    private static final String MOVIE_ID = "movie_id";
    private int movieId;
    @BindView(R.id.movie_detail_image)
    ImageView mImageView;
    @BindView(R.id.movie_detail_tabs)
    PagerSlidingTabStrip mPagerSlidingTabStrip;
    @BindView(R.id.movie_detail_view_pager)
    ViewPager mViewPager;

    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, DisplayDetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_movie);
        movieId = getMovieId();
        if (!isValidId(movieId)) {
            finish();
        }
        ButterKnife.bind(this);
        setupUI();
    }

    private void setupUI() {
        makeAPICAll();
        setupMovieImage();
        setupViewPager();
    }

    private void makeAPICAll() {
        
    }

    private void setupViewPager() {
    }

    private void setupMovieImage() {

    }

    private boolean isValidId(int movieId) {
        return movieId != -1;
    }

    private int getMovieId() {
        Intent intent = getIntent();
        return intent.getIntExtra(MOVIE_ID, -1);
    }
}
