package logic;

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
        Call<TheMovieDatabaseAPI.MovieListModel> call =
                service.getMoviesList(requestType.toString().toLowerCase());
        List<Movie> list = getMoviesList(call);
        if (list == null) {
            throw new MovieAPICallFailException();
        }
        return list;
    }

    private List<Movie> getMoviesList(Call<TheMovieDatabaseAPI.MovieListModel> call) {
        CallBackHandler callBackHandler = new CallBackHandler();
        call.enqueue(callBackHandler);
        return callBackHandler.returnList;
    }

    private class CallBackHandler implements Callback<TheMovieDatabaseAPI.MovieListModel> {
        List<Movie> returnList = null;

        @Override
        public void onResponse(Call<TheMovieDatabaseAPI.MovieListModel> call, Response<TheMovieDatabaseAPI.MovieListModel> response) {
            returnList = response.body().results;
        }

        @Override
        public void onFailure(Call<TheMovieDatabaseAPI.MovieListModel> call, Throwable t) {
        }
    }


    private TheMovieDatabaseAPI.RequestType getCorrespondingRequestType(int requestNumber) {
        TheMovieDatabaseAPI.RequestType types[] = TheMovieDatabaseAPI.RequestType.values();
        return types[requestNumber];
    }
}
