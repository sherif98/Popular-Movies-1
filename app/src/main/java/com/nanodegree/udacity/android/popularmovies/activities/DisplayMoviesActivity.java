package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MoviesFragmentPagerAdapter;
import adapters.TVShowsFragmentPagerAdapter;
import butterknife.ButterKnife;
import fragments.DetailMovieViewPagerFragment;
import fragments.DisplayMoviesFragment;
import logic.Movie;
import logic.TheMovieDatabaseAPI;

public class DisplayMoviesActivity extends AppCompatActivity implements DisplayMoviesFragment.CallBacks {
    private static final int MOVIES = 0;
    private static final int TV_SHOWS = 1;
    private TheMovieDatabaseAPI.Request mCurrentState;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);
        setupDrawerLayout();
        setupViewPager(TV_SHOWS);
    }

    private void setupViewPager(int position) {
        ViewPager viewPager = ButterKnife.findById(this, R.id.view_pager);
        PagerSlidingTabStrip tabStrip = ButterKnife.findById(this, R.id.tabs);
        FragmentPagerAdapter pagerAdapter = null;
        switch (position) {
            case MOVIES:
                pagerAdapter = new MoviesFragmentPagerAdapter(getSupportFragmentManager());
                mCurrentState = TheMovieDatabaseAPI.Request.MOVIE;
                break;
            case TV_SHOWS:
                pagerAdapter = new TVShowsFragmentPagerAdapter(getSupportFragmentManager());
                mCurrentState = TheMovieDatabaseAPI.Request.TV;
                break;
        }
//        viewPager.removeAllViewsInLayout();
//        viewPager.removeAllViews();
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void setupDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = ButterKnife.findById(this, R.id.drawer_layout);
        mDrawerList = ButterKnife.findById(this, R.id.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drawer_list_array)));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                setupViewPager(position);
            }
        });
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    public void onMovieClicked(Movie movie, boolean isFavorite) {
        if (!tabletUIRunning()) {
            Intent intent = DisplayDetailMovieActivity.newIntent(this, movie.getId(), isFavorite, mCurrentState);
            startActivity(intent);
        } else {
            Fragment fragment = DetailMovieViewPagerFragment.newInstance(movie.getId(), isFavorite
                    , mCurrentState);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_tablet_container, fragment)
                    .commit();
        }
    }

    private boolean tabletUIRunning() {
        return (findViewById(R.id.movie_detail_tablet_container) != null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
