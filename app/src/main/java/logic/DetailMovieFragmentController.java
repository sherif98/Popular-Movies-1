package logic;

import android.support.v4.app.Fragment;

import fragments.MovieDetailFragment;
import fragments.MovieReviewsFragment;
import fragments.MovieTrailersFragment;

public class DetailMovieFragmentController {
    private static final int FRAGMENTS_COUNT = 3;
    private static final int MOVIE_DETAIL = 0;
    private static final int MOVIE_REVIEW = 1;
    private static final int MOVIE_TRAILER = 2;
    private DetailMovie mDetailMovie;


    public DetailMovieFragmentController(DetailMovie detailMovie) {
        mDetailMovie = detailMovie;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case MOVIE_DETAIL:
                fragment = MovieDetailFragment.newInstance(mDetailMovie);
                break;
            case MOVIE_REVIEW:
                fragment = MovieReviewsFragment.newInstance(mDetailMovie);
                break;
            case MOVIE_TRAILER:
                fragment = MovieTrailersFragment.newInstance(mDetailMovie);
                break;
        }
        return fragment;
    }

    public int getCount() {
        return FRAGMENTS_COUNT;
    }



}
