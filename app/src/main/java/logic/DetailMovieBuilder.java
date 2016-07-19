package logic;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;

public class DetailMovieBuilder {
    private DetailMovie mDetailMovie;
    private String mMovieId;

    public DetailMovieBuilder(int movieId) {
        this.mMovieId = Integer.toString(movieId);
//        mDetailMovie = new DetailMovie();
        buildDetailMovie();
//        buildTrailers();
        Log.v("aftermethodcall", Boolean.toString(mDetailMovie == null));
//        buildReviews();
    }

    private void buildDetailMovie() {

        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<DetailMovie> call = service.getmovieData(mMovieId);
        Log.v("callisnull", "hmm?");
        try {
            mDetailMovie = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("didit", Boolean.toString(call.isExecuted()));
//        call.enqueue(new Callback<DetailMovie>() {
//            @Override
//            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
//                Log.v("insidemethod", Boolean.toString(mDetailMovie == null));
////                mDetailMovie = new DetailMovie(response.body().getBackdropPath());
//                mDetailMovie.setBackdropPath(response.body().getBackdropPath());
////                mDetailMovie = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<DetailMovie> call, Throwable t) {
//                Log.v("failincall", "here");
//            }
//        });
//        Log.v("wowwew", "itdid");
    }

//    private void buildReviews() {
//        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
//        Call<ReviewListModel> call = service.getMovieReview(mMovieId);
//        call.enqueue(new Callback<ReviewListModel>() {
//            @Override
//            public void onResponse(Call<ReviewListModel> call, Response<ReviewListModel> response) {
//                mDetailMovie.setReviews(response.body().results);
//            }
//
//            @Override
//            public void onFailure(Call<ReviewListModel> call, Throwable t) {
//
//            }
//        });
//    }

//    private void buildTrailers() {
//        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
//        Call<TrailerListModel> call = service.getMovieTrailers(mMovieId);
//        call.enqueue(new Callback<TrailerListModel>() {
//            @Override
//            public void onResponse(Call<TrailerListModel> call, Response<TrailerListModel> response) {
//                mDetailMovie.setTrailers(response.body().results);
//            }
//
//            @Override
//            public void onFailure(Call<TrailerListModel> call, Throwable t) {
//
//            }
//        });
//    }
//
    public DetailMovie getMovie() {
        return mDetailMovie;
    }

}


