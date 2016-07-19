package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;

import butterknife.ButterKnife;
import logic.DetailMovie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieReviewsFragment extends Fragment {


    public static MovieReviewsFragment newInstance(DetailMovie detailMovie) {
        MovieReviewsFragment fragment = new MovieReviewsFragment();
        Bundle args = new Bundle();
        args.putString("yeah", detailMovie.getBackdropPath());
        fragment.setArguments(args);
        return fragment;
    }

    public MovieReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        TextView textView = ButterKnife.findById(rootView, R.id.yeah);
        textView.setText(getArguments().getString("yeah"));
        return rootView;
    }

}
