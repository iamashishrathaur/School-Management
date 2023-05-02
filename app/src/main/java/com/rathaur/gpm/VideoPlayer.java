package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.StatusBarManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoPlayer extends AppCompatActivity {
  YouTubePlayerView youTubePlayerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Objects.requireNonNull(getSupportActionBar()).hide();
        youTubePlayerView=findViewById(R.id.youtube_player_view);
        youTubePlayerView.isFullScreen();
        youTubePlayerView.toggleFullScreen();
        youTubePlayerView.getEnableAutomaticInitialization();
        getLifecycle().addObserver(youTubePlayerView);

        try {
            youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                    youTubePlayer.cueVideo(Objects.requireNonNull(getIntent().getStringExtra("videoUrl")), 0);
                    youTubePlayer.loadVideo(Objects.requireNonNull(getIntent().getStringExtra("videoUrl")), 0);
                    youTubePlayer.play();
                }

                @Override
                public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {

                }

                @Override
                public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {
                }

                @Override
                public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {
                }

                @Override
                public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {

                }

                @Override
                public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    }