package logic;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("name")
    private String mName;
    @SerializedName("source")
    private String videoKey;

    public String getName() {
        return mName;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
