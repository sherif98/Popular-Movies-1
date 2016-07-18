package logic;

import android.util.Log;

import java.util.List;

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


    public List<Movie> getMoviesList(int requestNumber) throws MovieAPICallFailException {
        TheMovieDatabaseAPI.RequestType requestType = getCorrespondingRequestType(requestNumber);
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<MovieListModel> call =
                service.getMoviesList(requestType.toString().toLowerCase());
        Log.v("before", requestType.toString().toLowerCase());
        call.enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                Log.v("please", Boolean.toString(response.body().results == null));
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
                Log.v("callfailed", "a7a");
            }
        });
        return null;
    }
    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }
}
