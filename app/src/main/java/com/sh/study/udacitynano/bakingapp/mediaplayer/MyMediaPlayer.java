/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * ExoPlayer functionality based on Udacity's Classical Music Quiz App and ExoPlayer Demo
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
        if (mPlayer != null)
            return mPlayer.getPlayWhenReady();
        else
            return false;
    }

    public long getPosition() {
        if (mPlayer != null)
            return mPlayer.getCurrentPosition();
        else
            return 0;
    }

    public String getUri() {
        if (videoUri != null)
            return videoUri.toString();
        else
            return "";
    }

    public void setPosition(long position) {
        this.videoPosition = position;
    }

    public void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(mContext, "sessionId");
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MyMediaSessionCallback(mPlayer));
        mMediaSession.setActive(true);
    }

    public void initPlayer(SimpleExoPlayerView stepVideo, Boolean init) {
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            stepVideo.setPlayer(mPlayer);
            mPlayer.addListener(this);
        }
        if ((videoUri != null) && (!videoUri.equals(""))) {
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
