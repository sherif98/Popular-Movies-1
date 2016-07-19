package logic;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContet;

    public Review(String author, String contet) {
        mAuthor = author;
        mContet = contet;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContet() {
        return mContet;
    }
}
