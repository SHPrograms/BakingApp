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

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;
/*
 * Media Session Callbacks, where all external clients control the player.
 * Based on Udacity's Classical Music Quiz App
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
class MyMediaSessionCallback extends MediaSessionCompat.Callback {
    private SimpleExoPlayer player;

    public MyMediaSessionCallback(SimpleExoPlayer player) {
        this.player = player;
    }

    @Override
    public void onPlay() {
        player.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        player.setPlayWhenReady(false);
    }

    @Override
    public void onSkipToPrevious() {
        player.seekTo(0);
    }
}
