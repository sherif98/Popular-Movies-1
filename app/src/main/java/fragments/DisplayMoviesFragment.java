package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.udacity.android.popularmovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMoviesFragment extends Fragment {

    private final static String REQUEST_NUMBER = "request";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_movies, container, false);
    }
}
