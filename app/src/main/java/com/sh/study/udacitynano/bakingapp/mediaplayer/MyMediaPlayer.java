package com.sh.study.udacitynano.bakingapp.mediaplayer;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * ExoPlayer functionality is based on Udacity's Classical Music Quiz App and ExoPlayer Demo
 * {@see https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz}
 * {@see https://github.com/google/ExoPlayer}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class MyMediaPlayer implements ExoPlayer.EventListener {
    Context mContext;
    private SimpleExoPlayer mPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long videoPosition;
    private Uri videoUri;

    public MyMediaPlayer(Context context) {
        this.mContext = context;
    }

    public void setVideoUri(Uri video) {
        this.videoUri = video;
    }

    public boolean getPlayStatus() {
        return mPlayer.getPlayWhenReady();
    }

    public long getPosition() {
        return mPlayer.getCurrentPosition();
    }

    public String getUri() {
        if (videoUri != null) return videoUri.toString();
        else return "";
    }

    public void setPosition(long position) {
        this.videoPosition = position;
    }

    public void initializeMediaSession() {
        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(mContext, "seesionId");

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MyMediaSessionCallback(mPlayer));

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    public void initPlayer(SimpleExoPlayerView stepVideo, Boolean init) {
        if (mPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            stepVideo.setPlayer(mPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mPlayer.addListener(this);
        }
        if ((videoUri != null) && (!videoUri.equals(""))) {
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mContext, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mPlayer.seekTo(videoPosition);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(init);
        }
    }

    public void releasePlayer() {
        if (mPlayer != null) {
            videoPosition = mPlayer.getCurrentPosition();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void setActiveSession(boolean activeSession) {
        mMediaSession.setActive(activeSession);
    }

    /**
     * ExoPlayer
     *
     * @param playWhenReady
     * @param playbackState
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    /**
     * ExoPlayer
     *
     * @param timeline
     * @param manifest
     */
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    /**
     * ExoPlayer
     *
     * @param trackGroups
     * @param trackSelections
     */
    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    /**
     * ExoPlayer
     *
     * @param isLoading
     */
    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    /**
     * ExoPlayer
     *
     * @param error
     */
    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    /**
     * ExoPlayer
     */
    @Override
    public void onPositionDiscontinuity() {

    }
}
