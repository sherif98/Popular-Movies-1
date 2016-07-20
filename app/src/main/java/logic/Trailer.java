package logic;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Serializable{
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
