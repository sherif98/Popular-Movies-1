package logic;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    private String mVideoUrl;
    @SerializedName("name")
    private String mName;
    @SerializedName("site")
    private String mWebSite;
    private boolean mSupported;

    public Trailer(String videoUrl, String name, String webSite) {
        mVideoUrl = videoUrl;
        mName = name;
        mWebSite = webSite;
        mSupported = webSite.equals("YouTube");
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getName() {
        return mName;
    }

    public String getWebSite() {
        return mWebSite;
    }

    public boolean isSupported() {
        return mSupported;
    }
}
