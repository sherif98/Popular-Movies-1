package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import logic.DetailMovie;
import logic.Movie;

public class MovieDatabaseManager {
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

    public void addMovie(DetailMovie movie) {
        ContentValues contentValues = getContentValues(movie);
        new AddMovieTask().execute(contentValues);
    }

    public DetailMovie getDetailMovie(int movieId) {
        MovieCursorWrapper cursor = null;
        try {
            cursor = new GetDetailMovieTask().execute(movieId).get();
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

    private class AddMovieTask extends AsyncTask<ContentValues, Void, Void> {

        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            SQLiteDatabase database = mMovieDatabaseHelper.getWritableDatabase();
            database.insert(MovieDbSchema.MovieTable.NAME, null, contentValues[0]);
            database.close();
            return null;
        }
    }

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

        @Override
        protected MovieCursorWrapper doInBackground(Integer... id) {
            Log.v("whatistheid", id[0].toString());
            SQLiteDatabase database = mMovieDatabaseHelper.getReadableDatabase();
            Cursor cursor = database.query(MovieDbSchema.MovieTable.NAME, null,
                    MovieDbSchema.MovieTable.Cols.ID + " = ?",
                    new String[]{Integer.toString(id[0])}, null, null, null);
            return new MovieCursorWrapper(cursor);
        }
    }

}
