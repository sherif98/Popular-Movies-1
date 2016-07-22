package logic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Parcelable {
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContet;

    public Review(String author, String contet) {
        mAuthor = author;
        mContet = contet;
    }

    protected Review(Parcel in) {
        mAuthor = in.readString();
        mContet = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthor() {
        return mAuthor;
    }

    public String getContet() {
        return mContet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mContet);
    }
}
