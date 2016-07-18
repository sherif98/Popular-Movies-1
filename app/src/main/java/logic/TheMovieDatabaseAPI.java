package logic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDatabaseAPI {

    String RETROFIT_API_KEY = "?api_key=" + APIKeys.RETROFIT;
    String BASE_URL = "http://api.themoviedb.org/";

    enum RequestType {
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }


    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();


    @GET("/3/movie/{request}" + RETROFIT_API_KEY)
    Call<MovieListModel> getMoviesList(@Path("request") String request);

}

