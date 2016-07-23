package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DisplayMoviesFragment;
import logic.TheMovieDatabaseAPI;

public class TVShowsFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 4;
    private final String[] TABS_TITLES = {"ON_THE_AIR", "POPULAR", "TOP_RATED", "FAVORITES"};

    public TVShowsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DisplayMoviesFragment.newInstance(position, TheMovieDatabaseAPI.Request.TV);
    }

    @Override
    public long getItemId(int position) {
        return 5 * 100 + position;
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
