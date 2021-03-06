package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DisplayMoviesFragment;
import logic.TheMovieDatabaseAPI;

public class MoviesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 5;
    private final String[] TABS_TITLES = {"NOW_PLAYING", "POPULAR", "TOP_RATED", "UPCOMING", "FAVORITES"};

    public MoviesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DisplayMoviesFragment.newInstance(position,
                TheMovieDatabaseAPI.Request.MOVIE);
    }


    @Override
    public long getItemId(int position) {
        return 4 * 100 + position;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return TABS_TITLES[position];
    }
}
