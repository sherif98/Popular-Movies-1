package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import adapters.MoviesCardsAdapter;
import logic.Movie;
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
    private List<Movie> list;

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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int requestNumber = getRequestNumber();
        attachAdapter(requestNumber);
    }

    private int getRequestNumber() {
        Bundle args = getArguments();
        return args.getInt(REQUEST_NUMBER);
    }


    private void attachAdapter(int requestNumber) {
        TheMovieDatabaseAPI.RequestType requestType = getCorrespondingRequestType(requestNumber);
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call =
                service.getMoviesList(requestType.toString().toLowerCase());
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(
                        response.body().results, DisplayMoviesFragment.this);
                recyclerView.setAdapter(moviesCardsAdapter);
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Connect Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }

    @Override
    public void onClickMovie(Movie movie) {

    }
}
