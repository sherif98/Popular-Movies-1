package logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDatabaseAPI {

    String RETROFIT_API_KEY = "?api_key=" + APIKeys.RETROFIT;
    String BASE_URL = "https://api.themoviedb.org";

    enum RequestType {
        LATEST, NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();


    @GET("/3/movie/{request}" + RETROFIT_API_KEY)
    Call<MovieListModel> getMoviesList(@Path("request") String request);


    class MovieListModel {
        List<Movie> results;
    }
}