package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.List;

import adapters.MoviesCardsAdapter;
import logic.Movie;
import logic.MovieAPICallFailException;
import logic.MovieAPICaller;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMoviesFragment extends Fragment implements MoviesCardsAdapter.MovieClickListener {

    private final static String REQUEST_NUMBER = "request";

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
        RecyclerView recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_display_movies, container, false);
        Bundle args = getArguments();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int requestNumber = args.getInt(REQUEST_NUMBER);
        recyclerView.setAdapter(createAdapter(requestNumber));
        return recyclerView;
    }

    private RecyclerView.Adapter createAdapter(int requestNumber) {
        MovieAPICaller movieAPICaller = MovieAPICaller.getInstance();
        List<Movie> moviesList = null;
        try {
            moviesList = movieAPICaller.getMoviesList(requestNumber);
        } catch (MovieAPICallFailException e) {
            e.printStackTrace();
        }
        MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(moviesList, this);
        return moviesCardsAdapter;
    }

    @Override
    public void onClickMovie(Movie movie) {

    }
}
