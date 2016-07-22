package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.ArrayList;

import adapters.MovieReviewAdapter;
import butterknife.ButterKnife;
import logic.DetailMovie;
import logic.RecyclerViewEmptySupport;
import logic.Review;
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
        View rootView = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        RecyclerViewEmptySupport recyclerView =
                ButterKnife.findById(rootView, R.id.frag_movie_review_recycler_view);
        ReviewListModel reviewListModel = getArguments().getParcelable(REVIEWS);
        MovieReviewAdapter adapter = null;
        if (reviewListModel != null) {
            adapter = new MovieReviewAdapter(reviewListModel.results);
            Log.v("hereinreeview", Integer.toString(reviewListModel.results.size()));
        }
        setupRecyclerView(recyclerView, adapter);
        return rootView;
    }

    private void setupRecyclerView(RecyclerViewEmptySupport recyclerView, MovieReviewAdapter adapter) {
        if (adapter == null) {
            adapter = new MovieReviewAdapter(new ArrayList<Review>());
        }
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
