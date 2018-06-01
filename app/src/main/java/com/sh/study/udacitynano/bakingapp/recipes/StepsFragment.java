package com.sh.study.udacitynano.bakingapp.recipes;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import com.google.android.exoplayer2.util.Util;
import com.sh.study.udacitynano.bakingapp.mediaplayer.MyMediaPlayer;
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
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class StepsFragment extends Fragment implements VideoInterface {
    @BindView(R.id.recipe_step_video_url)
    SimpleExoPlayerView stepVideo;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.steps_rv)
    RecyclerView stepsRecyclerView;

    @BindView(R.id.horizontal_step)
    Guideline stepsHorizontalLine;

    private static final String CLASS_NAME = "IngredientsFragment";
    private static final String IS_PLAYING = "isPlaying";
    private static final String VIDEO_POSITION = "videoPosition";
    private static final String VIDEO_URI = "urlVideo";
    private static final String VIDEO_IMAGE = "videoImage";

    private Unbinder unbinder;
    private ArrayList<Step> steps;
    private Boolean twoPane;

    private MyMediaPlayer mediaPlayer;

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
            if (getArguments().containsKey(Constants.TWO_PANE)) {
                twoPane = getArguments().getBoolean(Constants.TWO_PANE);
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
        mediaPlayer = new MyMediaPlayer(getContext());
        mediaPlayer.initializeMediaSession();
        stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_50x50));
        if (savedInstanceState != null) {
            initializePlayer(
                    Uri.parse(savedInstanceState.getString(VIDEO_URI)),
                    savedInstanceState.getString(VIDEO_IMAGE),
                    savedInstanceState.getBoolean(IS_PLAYING),
                    savedInstanceState.getLong(VIDEO_POSITION));
            if ((getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    && (!twoPane)) {

                stepsRecyclerView.setVisibility(View.GONE);
                stepsHorizontalLine.setGuidelinePercent(1f);
                if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                }
            }
        } else {
            initializePlayer(null, null, false, 0);
            if ((getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    && (!twoPane)) {

                stepVideo.setVisibility(View.GONE);
                stepsHorizontalLine.setGuidelinePercent(0f);
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        mediaPlayer.releasePlayer();
        mediaPlayer.setActiveSession(false);
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mediaPlayer != null) {
            outState.putBoolean(IS_PLAYING, mediaPlayer.getPlayStatus());
            outState.putLong(VIDEO_POSITION, mediaPlayer.getPosition());
            outState.putString(VIDEO_URI, mediaPlayer.getUri());
            mediaPlayer.releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23) || (mediaPlayer.isPlayerNull())) {
            initializePlayer(Uri.parse(mediaPlayer.getUri()), null, mediaPlayer.getPlayStatus(), mediaPlayer.getPosition());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            mediaPlayer.releasePlayer();
        }
    }

    /**
     * VideoInterface
     *
     * @param
     * @param
     */
    @Override
    public void onClickVideo(String urlVideo, String urlImage) {
        initializePlayer(Uri.parse(urlVideo), urlImage, true, 0);
        if ((getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                && (!twoPane)) {

            stepsRecyclerView.setVisibility(View.GONE);
            stepsHorizontalLine.setGuidelinePercent(1f);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
            stepVideo.setVisibility(View.VISIBLE);
        }
    }

    private void initializePlayer(Uri uriVideo, String urlImage, Boolean init, long position) {
        if ((urlImage != null) && (!urlImage.equals(""))) {
            Picasso.with(getContext())
                    .load(urlImage)
                    .placeholder(R.drawable.placeholder_100x50)
                    .error(R.drawable.placeholder_50x50)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            stepVideo.setDefaultArtwork(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            stepVideo.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_100x50));
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
        }
        mediaPlayer.setVideoUri(uriVideo);
        mediaPlayer.setPosition(position);
        mediaPlayer.initPlayer(stepVideo, init);
    }
}
