package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import logic.DetailMovie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieTrailersFragment extends Fragment {


    public static MovieTrailersFragment newInstance(DetailMovie detailMovie) {
        return new MovieTrailersFragment();
    }

    public MovieTrailersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_trailers, container, false);
    }

}
