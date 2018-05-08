package com.sh.study.udacitynano.bakingapp.recipes;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Video was clicked
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-06
 */
public interface VideoInterface {
    void onClickVideo(String video, SimpleExoPlayerView view);
}
