package logic;


import com.google.gson.annotations.SerializedName;

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
    private String mPoSterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("vote_average")
    private double mVoteAverage;
    @SerializedName("reviews")
    private ReviewListModel mReviews;

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
        return mPoSterPath;
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
