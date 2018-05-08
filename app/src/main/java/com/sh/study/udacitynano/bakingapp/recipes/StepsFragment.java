package com.sh.study.udacitynano.bakingapp.recipes;

import android.app.NotificationManager;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment used to show list of steps in Recycler View.
 *
 * ExoPlayer functionality is based on Udacity's Classical Music Quiz App
 * {@see https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class StepsFragment extends Fragment implements VideoInterface, ExoPlayer.EventListener {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;


    private static final String CLASS_NAME = "IngredientsFragment";
    private Unbinder unbinder;
    private ArrayList<Step> steps;


    private SimpleExoPlayer mPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;


    public StepsFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
//        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
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

/*
        recipesViewModel.getRecipe().observe(this, steps -> {
            stepsAdapter.setSteps(steps.getSteps());
        });
*/

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

    private void releasePlayer() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
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
     * @param playWhenReady
     * @param playbackState
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        // TODO: Do I need that?
//        showNotification(mStateBuilder.build());
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
     * @param video
     */
    @Override
    public void onClickVideo(String video, SimpleExoPlayerView view) {
        initializePlayer(Uri.parse(video), view);

    }

    private void initializePlayer(Uri uri, SimpleExoPlayerView view) {
        if (mPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            view.setPlayer(mPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
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
    }
}
