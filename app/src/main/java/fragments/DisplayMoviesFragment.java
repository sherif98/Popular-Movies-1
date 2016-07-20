package fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.nanodegree.udacity.android.popularmovies.R;

import adapters.MoviesCardsAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import logic.Movie;
import logic.MovieListModel;
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
    private RecyclerView recyclerView;
    private CallBacks mCallbacks;

    /*
     * an interface to be implemented by the hosting activity
     */
    public interface CallBacks {
        void onMovieClicked(Movie movie);
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
        recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_display_movies, container, false);
        setupRecyclerView();
        return recyclerView;
    }

    private void setupRecyclerView() {
//        if (getActivity().findViewById(R.id.movie_detail_tablet_container) == null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        } else {
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        }

        int requestNumber = getRequestNumber();
        attachAdapter(requestNumber);
    }

    private int getRequestNumber() {
        Bundle args = getArguments();
        return args.getInt(REQUEST_NUMBER);
    }


    private void attachAdapter(int requestNumber) {
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
            }
        });
    }


    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }

    @Override
    public void onClickMovie(Movie movie) {
        mCallbacks.onMovieClicked(movie);
    }
}
