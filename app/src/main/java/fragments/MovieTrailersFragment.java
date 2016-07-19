package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import java.io.Serializable;
import java.util.List;

import adapters.MovieTrailerAdapter;
import logic.DetailMovie;
import logic.Trailer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieTrailersFragment extends Fragment implements MovieTrailerAdapter.TrailerClickListener {
    private static final String TRAILERS = "trailers";

    public static MovieTrailersFragment newInstance(DetailMovie detailMovie) {
        MovieTrailersFragment fragment = new MovieTrailersFragment();
        Bundle args = getArgument(detailMovie);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle getArgument(DetailMovie detailMovie) {
        Bundle args = new Bundle();
        List<Trailer> trailers = detailMovie.getTrailers().mTrailers;
        args.putSerializable(TRAILERS, (Serializable) trailers);
        return args;
    }

    public MovieTrailersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_movie_trailers, container, false);
        List<Trailer> trailers = getTrailerList();
        MovieTrailerAdapter adapter = new MovieTrailerAdapter(trailers, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return recyclerView;
    }

    private List<Trailer> getTrailerList() {
        Bundle args = getArguments();
        return (List<Trailer>) args.getSerializable(TRAILERS);
    }

    @Override
    public void onTrailerClicked(String link) {

    }
}
