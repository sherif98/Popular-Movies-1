package database;

public class MovieDbSchema {

    public static final class MovieTable {
        public static final String NAME = "movies";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String POSTER = "poster";
            public static final String BACK_IMAGE = "back_image";
            public static final String VOTE_AVERAGE = "vote_average";
            public static final String RELEASE_DATE = "release_date";
            public static final String OVERVIEW = "overview";
        }
    }
}
