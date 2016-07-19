package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import logic.DetailMovieFragmentController;
import logic.DetailType;

public class MovieDetailPagerAdapter extends FragmentPagerAdapter {
    DetailMovieFragmentController mController;

    public MovieDetailPagerAdapter(FragmentManager fm, DetailMovieFragmentController controller) {
        super(fm);
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
        return DetailType.values()[position].toString();
    }
}
