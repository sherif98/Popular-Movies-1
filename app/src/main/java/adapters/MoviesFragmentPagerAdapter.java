package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DisplayMoviesFragment;
import logic.TheMovieDatabaseAPI;

public class MoviesFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 4;
    private final TheMovieDatabaseAPI.RequestType[] TABS_TITLES = TheMovieDatabaseAPI.RequestType.values();
//    private final DisplayMoviesFragment[] mDisplayMoviesFragments = new DisplayMoviesFragment[4];

    public MoviesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
//        mDisplayMoviesFragments[0] = DisplayMoviesFragment.newInstance(0);
//        mDisplayMoviesFragments[1] = DisplayMoviesFragment.newInstance(1);
//        mDisplayMoviesFragments[2] = DisplayMoviesFragment.newInstance(2);
//        mDisplayMoviesFragments[3] = DisplayMoviesFragment.newInstance(3);
    }

    @Override
    public Fragment getItem(int position) {
//        if (mDisplayMoviesFragments[position] == null) {
//            mDisplayMoviesFragments[position] = DisplayMoviesFragment.newInstance(position);
//        }
//        return mDisplayMoviesFragments[position];
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
