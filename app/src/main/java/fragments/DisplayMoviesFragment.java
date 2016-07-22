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
    private final static String REQUEST_NUMBER = "request";
    private static final int FAVORITES_REQUSET_NUMBER = 4;
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

    @Override
    public void onResume() {
        super.onResume();
        if (getRequestNumber() == FAVORITES_REQUSET_NUMBER) {
            setupFavoriteMoviesFragment();
        }
    }

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
        Log.v("emptyviewnull", Boolean.toString(emptyView == null));
        recyclerView.setEmptyView(emptyView);
        int requestNumber = getRequestNumber();
        attachAdapter(requestNumber);
    }

    private int getRequestNumber() {
        Bundle args = getArguments();
        return args.getInt(REQUEST_NUMBER);
    }


    private void attachAdapter(int requestNumber) {
        if (requestNumber == FAVORITES_REQUSET_NUMBER) {
            isFavorite = true;
            setupFavoriteMoviesFragment();
        } else {
            isFavorite = false;
            setupOnlineRequestFragments(requestNumber);
        }
    }

    private void setupOnlineRequestFragments(int requestNumber) {
        TheMovieDatabaseAPI.RequestType requestType = getCorrespondingRequestType(requestNumber);
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call = service.getMoviesList(requestType.toString().toLowerCase());
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

    private void setupFavoriteMoviesFragment() {
//        MovieDatabaseManager movieDatabaseManager = MovieDatabaseManager.getInstance(getContext());
//        List<Movie> movies = movieDatabaseManager.getMovies();
//        MoviesCardsAdapter moviesCardsAdapter = new MoviesCardsAdapter(movies, DisplayMoviesFragment.this);
//        recyclerView.setAdapter(moviesCardsAdapter);
        new getMoviesTask().execute();
    }


    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }

    @Override
    public void onClickMovie(Movie movie) {
        mCallbacks.onMovieClicked(movie, isFavorite);
    }

    private class getMoviesTask extends AsyncTask<Void, Void, Void> {
        private Cursor cursor;

        @Override
        protected Void doInBackground(Void... voids) {
            MovieDatabaseHelper helper = new MovieDatabaseHelper(getContext());
            SQLiteDatabase database = helper.getReadableDatabase();
            cursor = database.query(MovieDbSchema.MovieTable.NAME, null, null, null,
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
