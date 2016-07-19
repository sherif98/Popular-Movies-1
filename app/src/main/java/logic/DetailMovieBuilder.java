package logic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieBuilder {
    private DetailMovie mDetailMovie;
    private String mMovieId;

    public DetailMovieBuilder(int movieId) {
        this.mMovieId = Integer.toString(movieId);
        buildDetailMovie();
        buildTrailers();
        buildReviews();
    }

    private void buildDetailMovie() {
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<DetailMovie> call = service.getmovieData(mMovieId);
        call.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                mDetailMovie = response.body();
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {

            }
        });
    }

    private void buildReviews() {
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<ReviewListModel> call = service.getMovieReview(mMovieId);
        call.enqueue(new Callback<ReviewListModel>() {
            @Override
            public void onResponse(Call<ReviewListModel> call, Response<ReviewListModel> response) {
                mDetailMovie.setReviews(response.body().results);
            }

            @Override
            public void onFailure(Call<ReviewListModel> call, Throwable t) {

            }
        });
    }

    private void buildTrailers() {
        TheMovieDatabaseAPI service = TheMovieDatabaseAPI.retrofit.create(TheMovieDatabaseAPI.class);
        Call<TrailerListModel> call = service.getMovieTrailers(mMovieId);
        call.enqueue(new Callback<TrailerListModel>() {
            @Override
            public void onResponse(Call<TrailerListModel> call, Response<TrailerListModel> response) {
                mDetailMovie.setTrailers(response.body().results);
            }

            @Override
            public void onFailure(Call<TrailerListModel> call, Throwable t) {

            }
        });
    }

    public DetailMovie getMovie() {
        return mDetailMovie;
    }

}

class TrailerListModel {
    List<Trailer> results;
}

class ReviewListModel {
    List<Review> results;
}
