package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nanodegree.udacity.android.popularmovies.R;

import fragments.DetailMovieViewPagerFragment;

public class DisplayDetailMovieActivity extends AppCompatActivity {
    private static final String MOVIE_ID = "movie_id";
//    private int movieId;
//    private ImageView mImageView;
//    private PagerSlidingTabStrip mPagerSlidingTabStrip;
//    private ViewPager mViewPager;


    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, DisplayDetailMovieActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detail_movie);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.movie_detail_fragment_container);
        if (fragment == null) {
            fragment = DetailMovieViewPagerFragment.newInstance(getMovieId());
            fragmentManager.beginTransaction().add(R.id.movie_detail_fragment_container,
                    fragment).commit();
        }
//        mImageView = ButterKnife.findById(this, R.id.movie_detail_image);
//        mViewPager = ButterKnife.findById(this, R.id.movie_detail_view_pager);
//        mPagerSlidingTabStrip = ButterKnife.findById(this, R.id.movie_detail_tabs);
//        movieId = getMovieId();
//        if (!isValidId(movieId)) {
//            finish();
//        }
//        setupUI();
    }

//    private void setupUI() {
//        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
//        Call<DetailMovie> call = service.getmovieData(Integer.toString(movieId));
//        final MovieDetailPagerAdapter detailPagerAdapter =
//                new MovieDetailPagerAdapter(getSupportFragmentManager());
//        call.enqueue(new Callback<DetailMovie>() {
//            @Override
//            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
//                DetailMovie detailMovie = response.body();
//                setupMovieImage(detailMovie.getBackdropPath());
//                setupViewPager(detailMovie, detailPagerAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<DetailMovie> call, Throwable t) {
//
//            }
//        });
//    }


//    private void setupViewPager(DetailMovie detailMovie, MovieDetailPagerAdapter detailPagerAdapter) {
//        DetailMovieFragmentController controller = new DetailMovieFragmentController(detailMovie);
//        detailPagerAdapter.setController(controller);
//        mViewPager.setAdapter(detailPagerAdapter);
//        mPagerSlidingTabStrip.setViewPager(mViewPager);
//    }

//    private void setupMovieImage(String imageUrl) {
//        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + imageUrl)
//                .into(mImageView);
//    }

    //    private boolean isValidId(int movieId) {
//        return movieId != -1;
//    }
//
    private int getMovieId() {
        Intent intent = getIntent();
        return intent.getIntExtra(MOVIE_ID, -1);
    }
}
