package database;

public class MovieDbSchema {

    public static final class MovieTable {
        public static final String NAME = "movies";

        public static final class Cols {
            public static final String TITLE = "title";
            public static final String POSTER = "poster";
            public static final String BACK_IMAGE = "back_image";
            public static final String VOTE_AVERAGE = "vote_average";
            public static final String RELEASE_DATE = "release_date";
            public static final String OVERVIEW = "overview";
            public static final String REVIEWS = "reviews";
            public static final String GENRES = "genres";
        }
    }

    public static final class GenreTable {
        public static final String NAME = "genres";

        public static final class Cols {
            public static final String NAME = "genre_name";
        }
    }

    public static final class ReviewTable {
        public static final String NAME = "reviews";

        public static final class Cols {
            public static final String AUTHOR = "author";
            public static final String CONTENT = "content";
        }
    }
}
