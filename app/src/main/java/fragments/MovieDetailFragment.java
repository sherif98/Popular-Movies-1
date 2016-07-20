package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import adapters.MoviesGenreAdapter;
import butterknife.ButterKnife;
import logic.DetailMovie;
import logic.Genre;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POSTER = "poster";
    private static final String MOVIE_VOTE = "vote";
    private static final String MOVIE_DATE = "date";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_GENRES = "genres";
    private static final String MOVIE_BACKDROP = "back_drop";

    public static MovieDetailFragment newInstance(DetailMovie detailMovie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = createArguments(detailMovie);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(DetailMovie detailMovie) {
        Bundle args = new Bundle();
        args.putString(MOVIE_TITLE, detailMovie.getTitle());
        args.putString(MOVIE_POSTER, detailMovie.getPosterPath());
        args.putString(MOVIE_VOTE, Double.toString(detailMovie.getVoteAverage()));
        args.putString(MOVIE_DATE, detailMovie.getReleaseDate());
        args.putString(MOVIE_OVERVIEW, detailMovie.getOverview());
        args.putString(MOVIE_BACKDROP, detailMovie.getBackdropPath());
        args.putSerializable(MOVIE_GENRES, (Serializable) detailMovie.getGenreList());
        return args;
    }


    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Bundle args = getArguments();
        setupUI(args, rootView);
        return rootView;
    }

    private void setupUI(Bundle args, View rootView) {
        setupBackdropPath(args.getString(MOVIE_BACKDROP), rootView);
        setupMovieTitle(args.getString(MOVIE_TITLE), rootView);
        setupMoviePoster(args.getString(MOVIE_POSTER), rootView);
        setupMovieDate(args.getString(MOVIE_DATE), rootView);
        setupMovieVote(args.getString(MOVIE_VOTE), rootView);
        setupMovieOverview(args.getString(MOVIE_OVERVIEW), rootView);
        setupGenres((List<Genre>) args.getSerializable(MOVIE_GENRES), rootView);
    }

    private void setupBackdropPath(String imageUrl, View rootView) {
        ImageView imageView = ButterKnife.findById(rootView, R.id.movie_detail_image);
        Picasso.with(imageView.getContext()).load("http://image.tmdb.org/t/p/w500/" + imageUrl)
                .into(imageView);
    }

    private void setupGenres(List<Genre> genres, View rootView) {
        RecyclerView recyclerView = ButterKnife.findById(rootView,
                R.id.movie_detail_genres_recycler_view);
        recyclerView.setAdapter(new MoviesGenreAdapter(genres));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

    }

    private void setupMovieOverview(String overview, View rootView) {
        TextView textView = ButterKnife.findById(rootView, R.id.movie_detail_overview);
        textView.setText(overview);
    }

    private void setupMovieVote(String vote, View rootView) {
        TextView textView = ButterKnife.findById(rootView, R.id.movie_detail_vote_average);
        textView.setText(vote + "/10");
    }

    private void setupMovieDate(String date, View rootView) {
        TextView textView = ButterKnife.findById(rootView, R.id.movie_detail_release_date);
        textView.setText(date);
    }

    private void setupMoviePoster(String url, View rootView) {
        ImageView imageView = ButterKnife.findById(rootView, R.id.movie_detil_poster);
        Picasso.with(imageView.getContext()).load("http://image.tmdb.org/t/p/w500/" + url)
                .into(imageView);
    }

    private void setupMovieTitle(String title, View rootView) {
        TextView textView = ButterKnife.findById(rootView, R.id.movie_detail_title);
        textView.setText(title);
    }


}
