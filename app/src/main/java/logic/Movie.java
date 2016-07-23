package logic;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String title;

    @SerializedName("original_title")
    private String movieTitle;

    private Bitmap posterBitmap;

    public Movie(String posterPath, int id, String title) {
        this.posterPath = posterPath;
        this.title = title;
        this.id = id;
    }

    public Movie(int id, Bitmap posterBitmap, String title) {
        this.id = id;
        this.posterBitmap = posterBitmap;
        this.title = title;
    }

    public String getTitle() {
        if (title == null) {
            return movieTitle;
        }
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }

    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

}
