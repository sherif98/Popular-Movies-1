package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MovieDetailPagerAdapter;
import butterknife.ButterKnife;
import database.MovieDatabaseManager;
import logic.DetailMovie;
import logic.DetailMovieFragmentController;
import logic.TheMovieDatabaseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovieViewPagerFragment extends Fragment {

    private static final String MOVIE_ID = "movie_id";
    private static final String FAVORITE_FLAG = "favorite";
    private String movieId;
    private boolean isFavorite;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;

    public static DetailMovieViewPagerFragment newInstance(int movieId, boolean isFavorite) {
        DetailMovieViewPagerFragment fragment = new DetailMovieViewPagerFragment();
        Bundle args = createArguments(movieId, isFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(int movieId, boolean isFavorite) {
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, Integer.toString(movieId));
        args.putBoolean(FAVORITE_FLAG, isFavorite);
        return args;
    }


    public DetailMovieViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_movie_container, container, false);
        mViewPager = ButterKnife.findById(view, R.id.movie_detail_view_pager);
        mPagerSlidingTabStrip = ButterKnife.findById(view, R.id.movie_detail_tabs);
        movieId = getMovieId();
        isFavorite = getFavoriteFlag();
        if (!isValidId(movieId)) {
            getActivity().finish();
        }
        setupUI();
        return view;
    }


    private void setupUI() {
        if (isFavorite) {
            setupUIFfomDatabase();
        } else {
            setupUIFromAPICall();
        }
    }

    private void setupUIFromAPICall() {
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<DetailMovie> call = service.getmovieData(movieId);
        final MovieDetailPagerAdapter detailPagerAdapter =
                new MovieDetailPagerAdapter(getChildFragmentManager());
        call.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                DetailMovie detailMovie = response.body();
                setupViewPager(detailMovie, detailPagerAdapter);
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
//                Toast.makeText(getActivity(), "Connect Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUIFfomDatabase() {
        DetailMovie movie = getDetailMovieFromDatabase();
        MovieDetailPagerAdapter detailPagerAdapter =
                new MovieDetailPagerAdapter(getChildFragmentManager());
        setupViewPager(movie, detailPagerAdapter);
    }


    private DetailMovie getDetailMovieFromDatabase() {
        MovieDatabaseManager manager = MovieDatabaseManager.getInstance(getContext());
        return manager.getDetailMovie(Integer.parseInt(movieId));
    }

    private void setupViewPager(DetailMovie detailMovie, MovieDetailPagerAdapter detailPagerAdapter) {
        DetailMovieFragmentController controller = new DetailMovieFragmentController(detailMovie);
        detailPagerAdapter.setController(controller);
        mViewPager.setAdapter(detailPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }


    private boolean isValidId(String movieId) {
        Integer id = Integer.parseInt(movieId);
        return !id.equals(-1);
    }

    private String getMovieId() {
        Bundle args = getArguments();
        return args.getString(MOVIE_ID);
    }

    private boolean getFavoriteFlag() {
        Bundle args = getArguments();
        return args.getBoolean(FAVORITE_FLAG);
    }
}
