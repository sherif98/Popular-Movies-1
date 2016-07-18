package logic;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;

    public Movie(String posterPath, int id, String title) {
        this.posterPath = posterPath;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getId() {
        return id;
    }
}
