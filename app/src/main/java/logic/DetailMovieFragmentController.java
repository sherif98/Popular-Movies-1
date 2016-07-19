package logic;

import android.support.v4.app.Fragment;

public class DetailMovieFragmentController {
    private static final int FRAGMENTS_COUNT = 3;
    private DetailMovie mDetailMovie;



    public DetailMovieFragmentController(DetailMovie detailMovie) {
        mDetailMovie = detailMovie;
    }

    public Fragment getFragment(int position) {
        return null;
    }

    public int getCount() {
        return FRAGMENTS_COUNT;
    }

}
