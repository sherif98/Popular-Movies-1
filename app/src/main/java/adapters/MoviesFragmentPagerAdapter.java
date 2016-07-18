package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DisplayMoviesFragment;
import logic.TheMovieDatabaseAPI;

public class MoviesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 4;
    private final TheMovieDatabaseAPI.RequestType[] TABS_TITLES = TheMovieDatabaseAPI.RequestType.values();

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

    @Override
    public CharSequence getPageTitle(int position) {

        return TABS_TITLES[position].toString();
    }
}
