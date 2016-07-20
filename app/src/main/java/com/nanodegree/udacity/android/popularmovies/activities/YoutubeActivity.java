package com.nanodegree.udacity.android.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nanodegree.udacity.android.popularmovies.R;

import logic.APIKeys;

public class YoutubeActivity extends YouTubeBaseActivity {

    private static final String VIDEO_KEY = "key";

    public static Intent newIntent(Context context, String videoKey) {
        Intent intent = new Intent(context, YoutubeActivity.class);
        intent.putExtra(VIDEO_KEY, videoKey);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(APIKeys.YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    Log.v("keyishere", getIntent().getStringExtra(VIDEO_KEY));
                    youTubePlayer.cueVideo(getIntent().getStringExtra(VIDEO_KEY));
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
