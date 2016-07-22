package fragments;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacity.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import adapters.MoviesGenreAdapter;
import butterknife.ButterKnife;
import database.DatabaseBitmapUtility;
import database.MovieDatabaseManager;
import logic.DetailMovie;
import logic.Genre;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POSTER = "poster";
    private static final String MOVIE_VOTE = "vote";
    private static final String MOVIE_DATE = "date";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_GENRES = "genres";
    private static final String MOVIE_BACKDROP = "back_drop";
    private static final String MOVIE_POSTER_BITMAP = "poster_bitmap";
    private static final String MOVIE_BACKDROP_BITMAP = "back_bitmap";
    private ImageView mPosterImageView;
    private ImageView mBackDropImage;

    public static MovieDetailFragment newInstance(DetailMovie detailMovie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = createArguments(detailMovie);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(DetailMovie detailMovie) {
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, detailMovie.getId());
        args.putString(MOVIE_TITLE, detailMovie.getTitle());
        args.putString(MOVIE_POSTER, detailMovie.getPosterPath());
        args.putString(MOVIE_VOTE, Double.toString(detailMovie.getVoteAverage()));
        args.putString(MOVIE_DATE, detailMovie.getReleaseDate());
        args.putString(MOVIE_OVERVIEW, detailMovie.getOverview());
        args.putString(MOVIE_BACKDROP, detailMovie.getBackdropPath());
        args.putSerializable(MOVIE_GENRES, (Serializable) detailMovie.getGenreList());
        if (detailMovie.getPosterBitmap() != null) {
            args.putByteArray(MOVIE_POSTER_BITMAP,
                    DatabaseBitmapUtility.getBytes(detailMovie.getPosterBitmap()));
        }
        if (detailMovie.getBackDropBitmap() != null) {
            args.putByteArray(MOVIE_BACKDROP_BITMAP,
                    DatabaseBitmapUtility.getBytes(detailMovie.getBackDropBitmap()));
        }

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
        setupAddToFavoriteButton(rootView);
        return rootView;
    }

    private void setupAddToFavoriteButton(View rootView) {
        final ImageButton button = ButterKnife.findById(rootView, R.id.movie_detail_add_favorites_button);
        final MovieDatabaseManager manager = MovieDatabaseManager.getInstance(getContext());
        int currentDisplayedMovieId = getMovieId(getArguments());
        if (manager.isMovieInDatabase(currentDisplayedMovieId)) {
            button.setEnabled(false);
            button.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_clicked));
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailMovie movie = getCurrentDisplayedMovie();
                    manager.addMovie(movie);
                    button.setEnabled(false);
                    button.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_clicked));
                }
            });
        }
    }

    private DetailMovie getCurrentDisplayedMovie() {
        Bundle args = getArguments();
        int id = getMovieId(args);
        String title = getMovieTitleData(args);
        String overview = getMovieOverviewData(args);
        String vote = getMovieVoteData(args);
        String date = getMovieReleaseDateData(args);
        Bitmap poster = getMoviePosterBitmap();
        Bitmap backDrop = getMovieBackDropBitmap();
        Log.v("titleisfrag", title);
        return new DetailMovie(id, title, overview, date, Double.parseDouble(vote), poster, backDrop);
    }

    private int getMovieId(Bundle args) {
        return args.getInt(MOVIE_ID);
    }

    private Bitmap getMovieBackDropBitmap() {
        if (mBackDropImage.getDrawable() == null) {
            return null;
        }
        return ((BitmapDrawable) mBackDropImage.getDrawable()).getBitmap();
    }

    private Bitmap getMoviePosterBitmap() {
        if (mPosterImageView.getDrawable() == null) {
            return null;
        }
        return ((BitmapDrawable) mPosterImageView.getDrawable()).getBitmap();
    }

    private void setupUI(Bundle args, View rootView) {
        setupBackdropPath(getMovieBackDropPathData(args), rootView);
        setupMovieTitle(getMovieTitleData(args), rootView);
        setupMoviePoster(getMoviePosterData(args), rootView);
        setupMovieDate(getMovieReleaseDateData(args), rootView);
        setupMovieVote(getMovieVoteData(args), rootView);
        setupMovieOverview(getMovieOverviewData(args), rootView);
        setupGenres(getMovieGenreData(args), rootView);
    }

    private String getMoviePosterData(Bundle args) {
        return args.getString(MOVIE_POSTER);
    }

    private String getMovieTitleData(Bundle args) {
        return args.getString(MOVIE_TITLE);
    }

    private String getMovieBackDropPathData(Bundle args) {
        return args.getString(MOVIE_BACKDROP);
    }

    private String getMovieReleaseDateData(Bundle args) {
        return args.getString(MOVIE_DATE);
    }

    private String getMovieVoteData(Bundle args) {
        return args.getString(MOVIE_VOTE);
    }

    private String getMovieOverviewData(Bundle args) {
        return args.getString(MOVIE_OVERVIEW);
    }

    private List<Genre> getMovieGenreData(Bundle args) {
        return (List<Genre>) args.getSerializable(MOVIE_GENRES);
    }

    private void setupBackdropPath(String imageUrl, View rootView) {
        mBackDropImage = ButterKnife.findById(rootView, R.id.movie_detail_image);
        if (imageUrl != null) {
            Picasso.with(mBackDropImage.getContext()).load("http://image.tmdb.org/t/p/w500/" + imageUrl)
                    .into(mBackDropImage);
        } else if (getArguments().getByteArray(MOVIE_BACKDROP_BITMAP) != null) {
            mBackDropImage.setImageBitmap(DatabaseBitmapUtility.getImage(
                    getArguments().getByteArray(MOVIE_BACKDROP_BITMAP)));
        }

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
        mPosterImageView = ButterKnife.findById(rootView, R.id.movie_detil_poster);
        if (url != null) {
            Picasso.with(mPosterImageView.getContext()).load("http://image.tmdb.org/t/p/w500/" + url)
                    .into(mPosterImageView);
        } else if (getArguments().getByteArray(MOVIE_POSTER_BITMAP) != null) {
            mPosterImageView.setImageBitmap(
                    DatabaseBitmapUtility.getImage(getArguments().getByteArray(MOVIE_POSTER_BITMAP)));
        }
    }

    private void setupMovieTitle(String title, View rootView) {
        TextView textView = ButterKnife.findById(rootView, R.id.movie_detail_title);
        textView.setText(title);
    }
}
