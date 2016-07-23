package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies_favorite_database";
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
            createTable(MovieDbSchema.MovieTable.NAME, database);
            createTable(MovieDbSchema.TVShowTable.NAME, database);
        }
        //control other vesrions of database as it is updated
    }

    private void createTable(String tableName, SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + tableName +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieDbSchema.MovieTable.Cols.ID + " INTEGER, " +
                MovieDbSchema.MovieTable.Cols.TITLE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.BACK_IMAGE + " BLOB, " +
                MovieDbSchema.MovieTable.Cols.POSTER + " BLOB, " +
                MovieDbSchema.MovieTable.Cols.VOTE_AVERAGE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.RELEASE_DATE + " TEXT, " +
                MovieDbSchema.MovieTable.Cols.OVERVIEW + " TEXT);"
        );
    }
}
