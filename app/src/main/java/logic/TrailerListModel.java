package logic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerListModel implements Parcelable {
    @SerializedName("youtube")
    public List<Trailer> mTrailers;


    private TrailerListModel(Parcel parcel) {
        mTrailers = new ArrayList<>();
        parcel.readList(mTrailers, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(mTrailers);
    }

    public static final Parcelable.Creator<TrailerListModel> CREATOR = new Creator<TrailerListModel>() {
        @Override
        public TrailerListModel createFromParcel(Parcel parcel) {
            return new TrailerListModel(parcel);
        }

        @Override
        public TrailerListModel[] newArray(int size) {
            return new TrailerListModel[size];
        }
    };
}
