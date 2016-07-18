package logic;

/**
 * Created by Admin on 7/18/2016.
 */
public class MovieAPICallFailException extends Exception {
    public MovieAPICallFailException() {
        super();
    }

    public MovieAPICallFailException(String message) {
        super(message);
    }
}
