package logic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDatabaseAPI {

    String RETROFIT_API_KEY = "?api_key=" + APIKeys.RETROFIT;
    //APIKeys is an interface containing your api key as a string field

    String BASE_URL = "http://api.themoviedb.org/";

    enum Request {
        MOVIE, TV
    }

    enum MovieRequestType {
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }

    enum TVShowRequestType {
        ON_THE_AIR, POPULAR, TOP_RATED
    }


    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();


    /*
     * use to get list of movies (popular movies, upcoming movies, etc..)
     */
    @GET("/3/{type}/{request}" + RETROFIT_API_KEY)
    Call<MovieListModel> getMoviesList(@Path("type") String type, @Path("request") String request);


    /*
     * use it to get details about a movie given the id (movie name, pictures, overview, etc..)
     */
    @GET("/3/{type}/{id}" + RETROFIT_API_KEY + "&append_to_response=reviews,trailers")
    Call<DetailMovie> getmovieData(@Path("type") String type, @Path("id") String id);

//    /*
//     * use it to get reviews of a movie given its id
//     */
//    @GET("/3/movie/{id}/reviews" + RETROFIT_API_KEY)
//    Call<ReviewListModel> getMovieReview(@Path("id") String id);
//
//
//    /*
//     * use it to get trailers of a movie givenn its id
//     */
//    @GET("/3/movie/{id}/videos" + RETROFIT_API_KEY)
//    Call<TrailerListModel> getMovieTrailers(@Path("id") String id);


}

