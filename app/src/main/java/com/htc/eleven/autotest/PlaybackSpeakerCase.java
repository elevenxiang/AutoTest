package com.htc.eleven.autotest;

import android.util.Log;

/**
 * Created by eleven on 17-9-19.
 */

public class PlaybackSpeakerCase extends Case {

    public static final String TAG = "PlaybackSpeakerCase";
    private TestPlayer myPlayer;
    @Override
    public boolean run() {

        boolean ret = false;

        myPlayer = new TestPlayer();
        myPlayer.start();

        ret = check();

        return ret;
    }

    @Override
    public boolean check() {
        Log.i(TAG, "check()");

        return true;
    }
}
