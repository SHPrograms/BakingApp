package com.sh.study.udacitynano.bakingapp.recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.Constants;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Step;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment used to show list of steps in Recycler View.
 * <p>
 * ExoPlayer functionality is based on Udacity's Classical Music Quiz App
 * {@see https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class StepsFragment extends Fragment implements VideoInterface, ExoPlayer.EventListener {
    @BindView(R.id.recipe_step_video_url)
    SimpleExoPlayerView stepVideo;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;

    private static final String CLASS_NAME = "IngredientsFragment";
    private Unbinder unbinder;
    private ArrayList<Step> steps;

    private SimpleExoPlayer mPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public StepsFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
        try {
            if (getArguments().containsKey(Constants.RECIPE_STEPS)) {
                steps = getArguments().getParcelableArrayList(Constants.RECIPE_STEPS);
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SHDebug.debugTag(CLASS_NAME, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assert stepsRecyclerView != null;
        StepsAdapter stepsAdapter = new StepsAdapter(this::onClickVideo);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setSteps(steps);
        initializeMediaSession();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        releasePlayer();
        mMediaSession.setActive(false);
        unbinder.unbind();
    }

    // TODO: Save and restore state of ExoPlayer?


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

    /**
     * VideoInterface
     *
     * @param
     * @param
     */
    @Override
    public void onClickVideo(String urlVideo, String urlImage) {
        initializePlayer(Uri.parse(urlVideo), urlImage);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void initializePlayer(Uri uriVideo, String urlImage) {

        // TODO: Error when Nutella Pie -> Step 5 (no Vid but thumb)
        // TODO: Check other recipes if every link is run properly if not and there is no way to manage via other codecs add error handling
        // TODO: Check speling errors for Baking if somewhere I don't have backing

        if ((urlImage != null) && (!urlImage.equals(""))) {
            Picasso.with(getContext())
                    .load(urlImage)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_50x50)
                    .error(R.drawable.placeholder_50x50)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            stepVideo.setDefaultArtwork(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_50x50));
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
}
                    });
        }

        if (mPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            stepVideo.setPlayer(mPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mPlayer.addListener(this);

        }
        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(uriVideo, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), "seesionId");

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

        stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_50x50));
    }
}
