package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import adapters.MoviesCardsAdapter;
import logic.Movie;
import logic.MovieAPICaller;
import logic.MovieListModel;
import logic.TheMovieDatabaseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMoviesFragment extends Fragment implements MoviesCardsAdapter.MovieClickListener {
    private static final String TAG = DisplayMoviesFragment.class.getSimpleName();
    private final static String REQUEST_NUMBER = "request";
    private RecyclerView recyclerView;

    public DisplayMoviesFragment() {
        // Required empty public constructor
    }

    public static DisplayMoviesFragment newInstance(int requestNumber) {
        DisplayMoviesFragment fragment = new DisplayMoviesFragment();
        setupFragmentArguments(fragment, requestNumber);
        return fragment;
    }

    private static void setupFragmentArguments(DisplayMoviesFragment fragment,
                                               int requestNumber) {
        Bundle args = new Bundle();
        args.putInt(REQUEST_NUMBER, requestNumber);
        fragment.setArguments(args);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_display_movies, container, false);
        setupRecyclerView();
        return recyclerView;
    }

    private void setupRecyclerView() {
        int requestNumber = getRequestNumber();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(createAdapter(requestNumber));
    }

    private int getRequestNumber() {
        Bundle args = getArguments();
        return args.getInt(REQUEST_NUMBER);
    }


    private RecyclerView.Adapter createAdapter(int requestNumber) {
        MovieAPICaller movieAPICaller = MovieAPICaller.getInstance();
        List<Movie> moviesList = null;
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call = service.getMoviesList(getCorrespondingRequestType(requestNumber)
                .toString().toLowerCase());
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                Log.v("fukoff", Boolean.toString(response.body().results == null));
                MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(response.body().results,
                        DisplayMoviesFragment.this);
                recyclerView.setAdapter(moviesCardsAdapter);
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {

            }
        });
//        try {
//            moviesList = movieAPICaller.getMoviesList(requestNumber);
//        } catch (MovieAPICallFailException e) {
////            e.printStackTrace();
//        }
//        Log.v(TAG, Boolean.toString(moviesList == null));
//        MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(moviesList, this);
        return null;
    }

    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }

    @Override
    public void onClickMovie(Movie movie) {

    }
}
