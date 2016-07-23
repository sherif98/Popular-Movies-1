package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import logic.DetailMovie;
import logic.Movie;

public class MovieDatabaseManager {
    private static final String TV = "tv";
    private static final String MOVIE = "movie";
    private static MovieDatabaseManager sInstance;
    private Context mContext;
    private MovieDatabaseHelper mMovieDatabaseHelper;

    private MovieDatabaseManager(Context context) {
        mContext = context.getApplicationContext();
        mMovieDatabaseHelper = new MovieDatabaseHelper(mContext);
    }

    public static MovieDatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieDatabaseManager(context);
        }
        return sInstance;
    }

    @Deprecated
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        MovieCursorWrapper cursor = null;
        try {
            cursor = new GetAllMoviesTask().execute().get();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return movies;
    }

    public void addMedia(DetailMovie media) {
        if (media.isTVShow()) {
            addMedia(media, MovieDbSchema.TVShowTable.NAME);
        } else {
            addMedia(media, MovieDbSchema.MovieTable.NAME);
        }
    }

    private void addMedia(DetailMovie media, String tableName) {
        ContentValues contentValues = getContentValues(media);
        new AddMediaTask(tableName).execute(contentValues);
    }

    public DetailMovie getDetailMedia(int mediaId, String request) {
        switch (request) {
            case TV:
                return getDetailMovie(mediaId, MovieDbSchema.TVShowTable.NAME);
            case MOVIE:
                return getDetailMovie(mediaId, MovieDbSchema.MovieTable.NAME);
        }
        return null;
    }

    private DetailMovie getDetailMovie(int movieId, String tableName) {
        MovieCursorWrapper cursor = null;
        try {
            cursor = new GetDetailMovieTask(tableName).execute(movieId).get();
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDetailMovie();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }

    public boolean isMovieInDatabase(int movieId, boolean isTVShow) {
        if (isTVShow) {
            return getDetailMedia(movieId, TV) != null;
        }
        return getDetailMedia(movieId, MOVIE) != null;
    }

    private ContentValues getContentValues(DetailMovie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDbSchema.MovieTable.Cols.ID, movie.getId());
        contentValues.put(MovieDbSchema.MovieTable.Cols.TITLE, movie.getTitle());
        contentValues.put(MovieDbSchema.MovieTable.Cols.POSTER, DatabaseBitmapUtility.getBytes(
                movie.getPosterBitmap()
        ));
        contentValues.put(MovieDbSchema.MovieTable.Cols.BACK_IMAGE, DatabaseBitmapUtility.getBytes(
                movie.getBackDropBitmap()
        ));
        contentValues.put(MovieDbSchema.MovieTable.Cols.OVERVIEW, movie.getOverview());
        contentValues.put(MovieDbSchema.MovieTable.Cols.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieDbSchema.MovieTable.Cols.VOTE_AVERAGE, movie.getVoteAverage());
        return contentValues;
    }

    private class AddMediaTask extends AsyncTask<ContentValues, Void, Void> {
        String tableName;

        public AddMediaTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();
            database.insert(tableName, null, contentValues[0]);
            database.close();
            return null;
        }
    }

    @Deprecated
    private class GetAllMoviesTask extends AsyncTask<Void, Void, MovieCursorWrapper> {

        @Override
        protected MovieCursorWrapper doInBackground(Void... voids) {
            SQLiteDatabase database = mMovieDatabaseHelper.getReadableDatabase();
            Cursor cursor = database.query(MovieDbSchema.MovieTable.NAME, null, null, null,
                    null, null, null);
            return new MovieCursorWrapper(cursor);
        }
    }

    private class GetDetailMovieTask extends AsyncTask<Integer, Void, MovieCursorWrapper> {
        String tableName;

        public GetDetailMovieTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        protected MovieCursorWrapper doInBackground(Integer... id) {
            SQLiteDatabase database = mMovieDatabaseHelper.getReadableDatabase();
            Cursor cursor = database.query(tableName, null,
                    MovieDbSchema.MovieTable.Cols.ID + " = ?",
                    new String[]{Integer.toString(id[0])}, null, null, null);
            return new MovieCursorWrapper(cursor);
        }
    }
}
