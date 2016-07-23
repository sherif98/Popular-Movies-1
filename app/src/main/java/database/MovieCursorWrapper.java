package database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;

import logic.DetailMovie;
import logic.Movie;

public class MovieCursorWrapper extends CursorWrapper {

    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    public static final int BACK_IMAGE_INDEX = 3;
    private static final int POSTER_INDEX = 4;
    public static final int VOTE_AVERAGE_INDEX = 5;
    public static final int RELEASE_DATE_INDEX = 6;
    public static final int OVERVIEW_INDEX = 7;

    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie() {
        String title = getString(TITLE_INDEX);
        byte[] poster = getBlob(POSTER_INDEX);
        int id = getInt(ID_INDEX);
        Bitmap posterBitmap = DatabaseBitmapUtility.getImage(poster);
        Movie movie = new Movie(id, posterBitmap, title);
        return movie;
    }


    public DetailMovie getDetailMovie() {
        int id = getInt(ID_INDEX);
        String title = getString(TITLE_INDEX);
        byte[] poster = getBlob(POSTER_INDEX);
        Bitmap posterBitmap = DatabaseBitmapUtility.getImage(poster);
        byte[] backImage = getBlob(BACK_IMAGE_INDEX);
        Bitmap backImageBitmap = DatabaseBitmapUtility.getImage(backImage);
        String voteAverage = getString(VOTE_AVERAGE_INDEX);
        String releaseDate = getString(RELEASE_DATE_INDEX);
        String overView = getString(OVERVIEW_INDEX);
        DetailMovie detailMovie = new DetailMovie(id, title, overView, releaseDate,
                Double.parseDouble(voteAverage), posterBitmap, backImageBitmap);
        return detailMovie;
    }
}
