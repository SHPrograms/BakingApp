package com.sh.study.udacitynano.bakingapp.recipes;

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;

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
