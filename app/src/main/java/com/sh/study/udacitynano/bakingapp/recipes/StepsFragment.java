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
package com.sh.study.udacitynano.bakingapp.recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

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

    private static final String CLASS_NAME = "IngredientsFragment";
    private static final String IS_PLAYING = "isPlaying";
    private static final String VIDEO_POSITION = "videoPosition";
    private static final String VIDEO_URI = "urlVideo";
    private static final String VIDEO_IMAGE = "videoImage";

    private Unbinder unbinder;
    private ArrayList<Step> steps;

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

    // TODO: Artwork is not saving and check recyclerView Position for tablets
    // TODO: Full screen mode.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_PLAYING, mediaPlayer.getPlayStatus());
        outState.putLong(VIDEO_POSITION, mediaPlayer.getPosition());
        outState.putString(VIDEO_URI, mediaPlayer.getUri());
        mediaPlayer.releasePlayer();
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
