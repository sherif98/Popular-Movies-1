package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import adapters.MovieDetailPagerAdapter;
import butterknife.ButterKnife;
import database.MovieDatabaseManager;
import logic.DetailMovie;
import logic.DetailMovieFragmentController;
import logic.TheMovieDatabaseAPI;
import logic.Trailer;
import logic.TrailerListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMovieViewPagerFragment extends Fragment {

    private static final String MOVIE_ID = "movie_id";
    private static final String FAVORITE_FLAG = "favorite";
    private static final String REQUEST_TYPE = "request_type";
    private String movieId;
    private boolean isFavorite;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private ShareActionProvider shareActionProvider;

    public static DetailMovieViewPagerFragment newInstance(int movieId, boolean isFavorite,
                                                           TheMovieDatabaseAPI.Request request) {
        DetailMovieViewPagerFragment fragment = new DetailMovieViewPagerFragment();
        Bundle args = createArguments(movieId, isFavorite, request);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(int movieId, boolean isFavorite, TheMovieDatabaseAPI.Request request) {
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, Integer.toString(movieId));
        args.putBoolean(FAVORITE_FLAG, isFavorite);
        args.putString(REQUEST_TYPE, request.toString().toLowerCase());
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
        if (!isFavorite) {
            setHasOptionsMenu(true);
        }
        setupUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_movie_menu, menu);
        MenuItem shareAction = menu.findItem(R.id.share_movie);
        shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareAction);
    }

    private Intent getShareIntent(DetailMovie movie) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String data = getShareData(movie);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        return intent;
    }

    private String getShareData(DetailMovie movie) {
        String data = movie.getTitle() + "\n\n";
        data = data + "Overview\n\n";
        data = data + movie.getOverview() + "\n\n";
        if (trailersFound(movie)) {
            data = data + getTrailer(movie);
        }
        return data;
    }

    private String getTrailer(DetailMovie movie) {

        return "Trailer:\n" + "https://www.youtube.com/watch?v=" +
                movie.getTrailers().mTrailers.get(0).getVideoKey();
    }

    private boolean trailersFound(DetailMovie movie) {
        if (movie.getTrailers() == null) {
            return false;
        }
        TrailerListModel model = movie.getTrailers();
        if (model.mTrailers == null) {
            return false;
        }
        List<Trailer> trailers = model.mTrailers;
        if (trailers.size() == 0) {
            return false;
        }
        return true;
    }

    private void setupUI() {
        if (isFavorite) {
            setupUIFfomDatabase();
        } else {
            setupUIFromAPICall();
        }
    }

    private String getRequest() {
        Bundle args = getArguments();
        return args.getString(REQUEST_TYPE);
    }

    private void setupUIFromAPICall() {

        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<DetailMovie> call = service.getmovieData(getRequest(), movieId);
        final MovieDetailPagerAdapter detailPagerAdapter =
                new MovieDetailPagerAdapter(getChildFragmentManager());
        call.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                DetailMovie detailMovie = response.body();
                setupViewPager(detailMovie, detailPagerAdapter);
                shareActionProvider.setShareIntent(getShareIntent(detailMovie));
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
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(getShareIntent(movie));
        }
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
