package logic;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrailerListModel implements Serializable {
    @SerializedName("youtube")
    public List<Trailer> mTrailers;
}
