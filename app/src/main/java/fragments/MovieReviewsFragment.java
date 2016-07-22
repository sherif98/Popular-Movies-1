package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MovieReviewAdapter;
import logic.DetailMovie;
import logic.ReviewListModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieReviewsFragment extends Fragment {
    private static final String REVIEWS = "reviews";

    public static MovieReviewsFragment newInstance(DetailMovie detailMovie) {
        MovieReviewsFragment fragment = new MovieReviewsFragment();
        Bundle args = createArguments(detailMovie);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(DetailMovie detailMovie) {
        Bundle args = new Bundle();
        args.putParcelable(REVIEWS, detailMovie.getReviews());
        return args;
    }

    public MovieReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        ReviewListModel reviewListModel = getArguments().getParcelable(REVIEWS);
        if (reviewListModel != null) {
            MovieReviewAdapter adapter = new MovieReviewAdapter(reviewListModel.results);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return recyclerView;
    }
}
