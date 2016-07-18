package logic;

import android.util.Log;

import adapters.MoviesCardsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieAPICaller {

    private static MovieAPICaller instance;

    private MovieAPICaller() {
    }

    public static MovieAPICaller getInstance() {

        if (instance == null) {
            synchronized (MovieAPICaller.class) {
                if (instance == null) {
                    instance = new MovieAPICaller();
                }
            }
        }
        return instance;
    }


    public MoviesCardsAdapter getMoviesList(int requestNumber, final MoviesCardsAdapter moviesCardsAdapter)
            throws MovieAPICallFailException {
        TheMovieDatabaseAPI.RequestType requestType = getCorrespondingRequestType(requestNumber);
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call =
                service.getMoviesList(requestType.toString().toLowerCase());
        Log.v("before", requestType.toString().toLowerCase());
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
//                moviesCardsAdapter.setMoviesList(response.body().results);
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {

            }
        });
        return moviesCardsAdapter;
    }


    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }
}
