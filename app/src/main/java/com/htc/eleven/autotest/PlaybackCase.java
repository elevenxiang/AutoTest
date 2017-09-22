package com.htc.eleven.autotest;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by eleven on 17-9-22.
 */

public abstract class PlaybackCase extends Case {

    public class TestPlayer {

        private static final String TAG = "TestPlayer";

        private final static String mMusicFIle = "test.mp3";
        private AssetFileDescriptor mFd = null;
        private MediaPlayer player = null;
        private Boolean mRunning = false;

        public TestPlayer() {

            // use AssertFileDescriptor to access music file.
            try {
                mFd = App.getApp().getAssets().openFd(mMusicFIle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void preparePlay(){

            if(player==null) {
                player = new MediaPlayer();
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlay();
                }
            });

            try {
                // use AssertFileDescriptor to setDataSource().
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    player.setDataSource(mFd);
                } else
                    player.setDataSource(mFd.getFileDescriptor());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private boolean startPlay() {
            if(!mRunning) {
                player.start();
                mRunning = true;
            }

            return true;

        }

        private boolean stopPlay() {
            if(mRunning) {
                player.stop();
                player.reset();
                player.release();
                player = null;

                mRunning = false;
            }

            return true;
        }
        public boolean start() {
            Log.i(TAG, "start play");
            preparePlay();
            return startPlay();
        }

        public boolean stop() {
            Log.i(TAG, "stop play");
            return stopPlay();
        }

    }

    private TestPlayer myPlayer;

    @Override
    public boolean run() {

        boolean ret;

        myPlayer = new TestPlayer();
        myPlayer.start();

        ret = check();

        myPlayer.stop();

        return ret;
    }
}
