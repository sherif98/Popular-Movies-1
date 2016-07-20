package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MoviesFragmentPagerAdapter;
import butterknife.ButterKnife;
import fragments.DetailMovieViewPagerFragment;
import fragments.DisplayMoviesFragment;
import logic.Movie;

public class DisplayMoviesActivity extends AppCompatActivity implements DisplayMoviesFragment.CallBacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);
        ViewPager viewPager = ButterKnife.findById(this, R.id.view_pager);
        viewPager.setAdapter(new MoviesFragmentPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = ButterKnife.findById(this, R.id.tabs);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        if (!tabletUIRunning()) {
            Intent intent = DisplayDetailMovieActivity.newIntent(this, movie.getId());
            startActivity(intent);
        } else {
            Fragment fragment = DetailMovieViewPagerFragment.newInstance(movie.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_tablet_container, fragment)
                    .commit();
        }
    }

    private boolean tabletUIRunning() {
        return (findViewById(R.id.movie_detail_tablet_container) != null);
    }
}
