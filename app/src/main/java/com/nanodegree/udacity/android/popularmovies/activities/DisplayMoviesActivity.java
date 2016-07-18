package com.nanodegree.udacity.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MoviesFragmentPagerAdapter;

public class DisplayMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MoviesFragmentPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabStrip.setViewPager(mViewPager);
    }
}
