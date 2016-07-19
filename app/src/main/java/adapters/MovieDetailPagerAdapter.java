package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import logic.DetailMovieFragmentController;

public class MovieDetailPagerAdapter extends FragmentPagerAdapter {
    DetailMovieFragmentController mController;
    private final static String[] detailType = {"MOVIE", "REVIEWS", "TRAILERS"};

    public MovieDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setController(DetailMovieFragmentController controller) {
        mController = controller;
    }

    @Override
    public Fragment getItem(int position) {
        return mController.getFragment(position);
    }

    @Override
    public int getCount() {
        return mController.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return detailType[0];
    }
}
