package logic;

public class Movie {
    private String poster_path;
    private int id;
    private String title;

    public Movie(String poster_path, int id, String title) {
        this.poster_path = poster_path;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public int getId() {
        return id;
    }
}
