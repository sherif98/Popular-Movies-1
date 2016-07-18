package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DisplayMoviesFragment;

public class MoviesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 6;

    public MoviesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DisplayMoviesFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
