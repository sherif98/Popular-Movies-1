package logic;


import com.google.gson.annotations.SerializedName;

public class DetailMovie {
    @SerializedName("backdrop_path")
    public String mBackdropPath;
    //    @SerializedName("genres")
//    private List<Genre> mGenreList;
//    @SerializedName("id")
//    private int mId;
//    @SerializedName("original_title")
//    private String mTitle;
//    @SerializedName("overview")
//    private String mOverview;
//    @SerializedName("poster_path")
//    private String mPoSterPath;
//    @SerializedName("release_date")
//    private String mReleaseDate;
//    @SerializedName("vote_average")
//    private int mVoteAverage;

//    private List<Trailer> mTrailers;
//    private List<Review> mReviews;


//    public DetailMovie(String backdropPath/*, int id,
//                       String title, String overview, String poSterPath,
//                       String releaseDate, int voteAverage*/) {
//        mBackdropPath = backdropPath;
////        mGenreList = genreList;
////        mId = id;
////        mTitle = title;
////        mOverview = overview;
////        mPoSterPath = poSterPath;
////        mReleaseDate = releaseDate;
////        mVoteAverage = voteAverage;
//    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

//    public List<Genre> getGenreList() {
//        return mGenreList;
//    }

//    public int getId() {
//        return mId;
//    }
//
//    public String getTitle() {
//        return mTitle;
//    }
//
//    public String getOverview() {
//        return mOverview;
//    }
//
//    public String getPoSterPath() {
//        return mPoSterPath;
//    }
//
//    public String getReleaseDate() {
//        return mReleaseDate;
//    }
//
//    public int getVoteAverage() {
//        return mVoteAverage;
//    }

//    public List<Review> getReviews() {
//        return mReviews;
//    }
//
//    public void setReviews(List<Review> reviews) {
//        mReviews = reviews;
//    }
//
//    public List<Trailer> getTrailers() {
//        return mTrailers;
//    }
//
//    public void setTrailers(List<Trailer> trailers) {
//        mTrailers = trailers;
//    }
}
