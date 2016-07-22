package logic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Parcelable{
    @SerializedName("name")
    private String mName;
    @SerializedName("source")
    private String videoKey;

    protected Trailer(Parcel in) {
        mName = in.readString();
        videoKey = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getVideoKey() {
        return videoKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(videoKey);
    }
}
