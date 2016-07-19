package logic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDatabaseAPI {

    String RETROFIT_API_KEY = "?api_key=" + APIKeys.RETROFIT;
    //APIKeys is an interface containing your api key as a string field

    String BASE_URL = "http://api.themoviedb.org/";

    enum RequestType {
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }


    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();


    /*
     * use to get list of movies (popular movies, upcoming movies, etc..)
     */
    @GET("/3/movie/{request}" + RETROFIT_API_KEY)
    Call<MovieListModel> getMoviesList(@Path("request") String request);


    /*
     * use it to get details about a movie given the id (movie name, pictures, overview, etc..)
     */
    @GET("/3/movie/{id}" + RETROFIT_API_KEY)
    Callback<DetailMovie> getmovieData(@Path("id") String id);

    /*
     * use it to get reviews of a movie given its id
     */
    @GET("/3/movie/{id}/reviews" + RETROFIT_API_KEY)
    Callback<ReviewListModel> getMovieReview(@Path("id") String id);


    /*
     * use it to get trailers of a movie givenn its id
     */
    @GET("/3/movie/{id}/videos" + RETROFIT_API_KEY)
    Callback<TrailerListModel> getMovieTrailers(@Path("id") String id);


}

