package logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ReviewListModel implements Parcelable {
    public List<Review> results;

    protected ReviewListModel(Parcel in) {
        results = new ArrayList<>();
        in.readList(results, null);
    }

    public static final Creator<ReviewListModel> CREATOR = new Creator<ReviewListModel>() {
        @Override
        public ReviewListModel createFromParcel(Parcel in) {
            return new ReviewListModel(in);
        }

        @Override
        public ReviewListModel[] newArray(int size) {
            return new ReviewListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(results);
    }
}
