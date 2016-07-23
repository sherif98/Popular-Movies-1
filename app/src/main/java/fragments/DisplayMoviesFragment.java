package fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.nanodegree.udacity.android.popularmovies.R;

import java.util.ArrayList;

import adapters.MoviesCardsAdapter;
import adapters.RecyclerViewCursorAdapter;
import butterknife.ButterKnife;
import database.MovieCursorWrapper;
import database.MovieDatabaseHelper;
import database.MovieDbSchema;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import logic.Movie;
import logic.MovieListModel;
import logic.RecyclerViewEmptySupport;
import logic.TheMovieDatabaseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMoviesFragment extends Fragment implements MoviesCardsAdapter.MovieClickListener {
    private static final String TAG = DisplayMoviesFragment.class.getSimpleName();
    private final static String REQUEST_NUMBER = "request_number";
    private final static String REQUEST_TYPE = "request_type";
    private static final int MOVIES_FAVORITES_REQUSET_NUMBER = 4;
    private static final int TV_FAVORITES_REQUSET_NUMBER = 3;
    private RecyclerViewEmptySupport recyclerView;
    private CallBacks mCallbacks;
    private boolean isFavorite;
    private Cursor mCursor;
    private SQLiteDatabase mDatabase;

    /*
     * an interface to be implemented by the hosting activity
     */
    public interface CallBacks {
        void onMovieClicked(Movie movie, boolean isFavorite);
    }


    public DisplayMoviesFragment() {
        // Required empty public constructor
    }

    public static DisplayMoviesFragment newInstance(int requestNumber
            , TheMovieDatabaseAPI.Request requestType) {
        DisplayMoviesFragment fragment = new DisplayMoviesFragment();
        Bundle args = createArguments(requestNumber, requestType);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createArguments(int requestNumber, TheMovieDatabaseAPI.Request requestType) {
        Bundle args = new Bundle();
        args.putInt(REQUEST_NUMBER, requestNumber);
        args.putString(REQUEST_TYPE, requestType.toString());
        return args;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallBacks) {
            mCallbacks = (CallBacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //TODO save instance
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_display_movies, container, false);
        recyclerView = ButterKnife.findById(rootView, R.id.frag_display_movies_recycler_view);
        setupRecyclerView();
        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getRequestType() == TheMovieDatabaseAPI.Request.MOVIE) {
//            if (getRequestNumber() == MOVIES_FAVORITES_REQUSET_NUMBER) {
//                setupFavoriteMoviesFragment(MovieDbSchema.MovieTable.NAME);
//            }
//        } else {
//            if (getRequestNumber() == TV_FAVORITES_REQUSET_NUMBER) {
//                setupFavoriteMoviesFragment(MovieDbSchema.TVShowTable.NAME);
//            }
//        }
//    }
    //TODO fix onResume to avoid duplicate call

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        recyclerView.setEmptyView(emptyView);
        int requestNumber = getRequestNumber();
        TheMovieDatabaseAPI.Request requestType = getRequestType();
        attachAdapter(requestType, requestNumber);
    }

    private int getRequestNumber() {
        Bundle args = getArguments();
        return args.getInt(REQUEST_NUMBER);
    }

    private TheMovieDatabaseAPI.Request getRequestType() {
        Bundle args = getArguments();
        TheMovieDatabaseAPI.Request request =
                TheMovieDatabaseAPI.Request.valueOf(args.getString(REQUEST_TYPE));
        return request;
    }


    private void attachAdapter(TheMovieDatabaseAPI.Request requestType, int requestNumber) {
        switch (requestType) {
            case MOVIE:
                attachMovieAdapter(requestNumber);
                break;
            case TV:
                Log.v("numberissss", Integer.toString(requestNumber));
                attachTvShowAdapter(requestNumber);
        }

    }

    private void attachTvShowAdapter(int requestNumber) {
        //TODO make favorite TV favorite list
        if (requestNumber == TV_FAVORITES_REQUSET_NUMBER) {
            isFavorite = true;
            setupFavoriteMoviesFragment(MovieDbSchema.TVShowTable.NAME);
        } else {
            isFavorite = false;
            TheMovieDatabaseAPI.TVShowRequestType requestType =
                    getCorrespondingTVShowRequestType(requestNumber);
            setupOnlineRequestFragments(TheMovieDatabaseAPI.Request.TV.toString().toLowerCase()
                    , requestType.toString().toLowerCase());
        }
    }

    private void attachMovieAdapter(int requestNumber) {
        if (requestNumber == MOVIES_FAVORITES_REQUSET_NUMBER) {
            isFavorite = true;
            setupFavoriteMoviesFragment(MovieDbSchema.MovieTable.NAME);
        } else {
            isFavorite = false;
            TheMovieDatabaseAPI.MovieRequestType requestType =
                    getCorrespondingMovieRequestType(requestNumber);
            setupOnlineRequestFragments(TheMovieDatabaseAPI.Request.MOVIE.toString().toLowerCase()
                    , requestType.toString().toLowerCase());
        }
    }

    private void setupOnlineRequestFragments(String type, String request) {

        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call = service.getMoviesList(type, request);
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(
                        response.body().results, DisplayMoviesFragment.this);
                recyclerView.setAdapter(moviesCardsAdapter);
                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
//                Toast.makeText(getActivity(), "Connect Problem", Toast.LENGTH_SHORT).show();
                MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(
                        new ArrayList<Movie>(), DisplayMoviesFragment.this);
                recyclerView.setAdapter(moviesCardsAdapter);
            }
        });
    }

    private void setupFavoriteMoviesFragment(String tableName) {
//        MovieDatabaseManager movieDatabaseManager = MovieDatabaseManager.getInstance(getContext());
//        List<Movie> movies = movieDatabaseManager.getMovies();
//        MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(movies, DisplayMoviesFragment.this);
//        recyclerView.setAdapter(moviesCardsAdapter);
        new getMoviesTask().execute(tableName);
    }


    private TheMovieDatabaseAPI.MovieRequestType getCorrespondingMovieRequestType(int requestNumber) {
        TheMovieDatabaseAPI.MovieRequestType types[] = TheMovieDatabaseAPI.MovieRequestType.values();
        return types[requestNumber];
    }

    private TheMovieDatabaseAPI.TVShowRequestType getCorrespondingTVShowRequestType(int requestNumber) {
        TheMovieDatabaseAPI.TVShowRequestType types[] = TheMovieDatabaseAPI.TVShowRequestType.values();
        return types[requestNumber];
    }

    @Override
    public void onClickMovie(Movie movie) {
        mCallbacks.onMovieClicked(movie, isFavorite);
    }

    private class getMoviesTask extends AsyncTask<String, Void, Void> {
        private Cursor cursor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... tableName) {
            MovieDatabaseHelper helper = new MovieDatabaseHelper(getContext());
            SQLiteDatabase database = helper.getReadableDatabase();
            cursor = database.query(tableName[0], null, null, null,
                    null, null, null);
            mCursor = cursor;
            mDatabase = database;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            MovieCursorWrapper movieCursorWrapper = new MovieCursorWrapper(cursor);
            RecyclerViewCursorAdapter adapter = new RecyclerViewCursorAdapter(movieCursorWrapper,
                    DisplayMoviesFragment.this);
            recyclerView.setAdapter(adapter);
        }
    }
}
