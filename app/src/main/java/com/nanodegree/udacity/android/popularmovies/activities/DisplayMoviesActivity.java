package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MoviesFragmentPagerAdapter;
import butterknife.ButterKnife;
import fragments.DisplayMoviesFragment;
import logic.Movie;

public class DisplayMoviesActivity extends AppCompatActivity implements DisplayMoviesFragment.CallBacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        ViewPager viewPager = ButterKnife.findById(this, R.id.view_pager);
        viewPager.setAdapter(new MoviesFragmentPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = ButterKnife.findById(this, R.id.tabs);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = DisplayDetailMovieActivity.newIntent(this, movie.getId());
        startActivity(intent);
        //TODO handle tablet layout and phone layout
    }
}
