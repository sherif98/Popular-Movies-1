package logic;


import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DetailMovie {
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genres")
    private List<Genre> mGenreList;
    @SerializedName("id")
    private int mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("vote_average")
    private double mVoteAverage;
    @SerializedName("reviews")
    private ReviewListModel mReviews;
    @SerializedName("trailers")
    private TrailerListModel mTrailers;
    private Bitmap mPosterBitmap;
    private Bitmap mBackDropBitmap;

    public DetailMovie(String backdropPath, List<Genre> genreList, int id, String title,
                       String overview, String poSterPath, String releaseDate,
                       double voteAverage, ReviewListModel reviews, TrailerListModel trailers) {
        mBackdropPath = backdropPath;
        mGenreList = genreList;
        mId = id;
        mTitle = title;
        mOverview = overview;
        mPosterPath = poSterPath;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mReviews = reviews;
        mTrailers = trailers;
    }

    //constructor used by the SQLite database
    public DetailMovie(int id, String title, String overview,
                       String releaseDate, double voteAverage,
                       Bitmap posterBitmap, Bitmap backDropBitmap) {
        this.mId = id;
        mTitle = title;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mPosterBitmap = posterBitmap;
        mBackDropBitmap = backDropBitmap;
        mGenreList = new ArrayList<>();
    }

    public Bitmap getBackDropBitmap() {
        return mBackDropBitmap;
    }

    public Bitmap getPosterBitmap() {
        return mPosterBitmap;
    }

    public TrailerListModel getTrailers() {
        return mTrailers;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public List<Genre> getGenreList() {
        return mGenreList;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public ReviewListModel getReviews() {
        return mReviews;
    }
}
