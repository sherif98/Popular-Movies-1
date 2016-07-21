package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popular_movie_database";
    private static final int DATABASE_VERSION = 1;

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateMyDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            createGenreTable(database);
            createReviewTable(database);
            createMovieTable(database);
        }
        //control other vesrions of database as it is updated
    }

    private void createReviewTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + MovieDbSchema.ReviewTable.NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDbSchema.ReviewTable.Cols.AUTHOR + " TEXT, " +
                MovieDbSchema.ReviewTable.Cols.CONTENT + " TEXT);"
        );
    }

    private void createGenreTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + MovieDbSchema.GenreTable.NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDbSchema.GenreTable.Cols.NAME + " TEXT);"
        );
    }

    private void createMovieTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + MovieDbSchema.MovieTable.NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDbSchema.MovieTable.Cols.TITLE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.BACK_IMAGE + " BLOB, " +
                MovieDbSchema.MovieTable.Cols.POSTER + " BLOB, " +
                MovieDbSchema.MovieTable.Cols.VOTE_AVERAGE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.RELEASE_DATE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.GENRES + " INTEGER, " +
                MovieDbSchema.MovieTable.Cols.OVERVIEW + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.REVIEWS + " INTEGER, " +
                "FOREIGN KEY(" + MovieDbSchema.MovieTable.Cols.GENRES + ") PREFERENCES " +
                MovieDbSchema.GenreTable.NAME + "(_id), " +
                "FOREIGN KEY(" + MovieDbSchema.MovieTable.Cols.REVIEWS + ") PREFERENCES " +
                MovieDbSchema.ReviewTable.NAME + "(_id));"
        );
    }
}
