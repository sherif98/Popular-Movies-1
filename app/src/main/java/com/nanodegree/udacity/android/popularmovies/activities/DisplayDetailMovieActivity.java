package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import adapters.MovieDetailPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import logic.DetailMovie;
import logic.DetailMovieBuilder;
import logic.DetailMovieFragmentController;

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
        DetailMovie detailMovie = buildMovie();
        setupMovieImage(detailMovie.getBackdropPath());
        setupViewPager(detailMovie);
    }

    private DetailMovie buildMovie() {
        DetailMovieBuilder detailMovieBuilder = new DetailMovieBuilder(movieId);
        return detailMovieBuilder.getMovie();
    }


    private void setupViewPager(DetailMovie detailMovie) {
        DetailMovieFragmentController controller = new DetailMovieFragmentController(detailMovie);
        mViewPager.setAdapter(new MovieDetailPagerAdapter(getSupportFragmentManager(), controller));
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    private void setupMovieImage(String imageUrl) {
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + imageUrl).into(mImageView);
    }

    private boolean isValidId(int movieId) {
        return movieId != -1;
    }

    private int getMovieId() {
        Intent intent = getIntent();
        return intent.getIntExtra(MOVIE_ID, -1);
    }
}
