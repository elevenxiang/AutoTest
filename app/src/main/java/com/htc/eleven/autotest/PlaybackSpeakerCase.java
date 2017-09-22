package com.htc.eleven.autotest;

import android.util.Log;

import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public class PlaybackSpeakerCase extends PlaybackCase {

    public static final String TAG = "PlaybackSpeakerCase";
    public static final boolean DEBUG = true;

    public class AudioConditionMixerControl extends Condition {

        public AudioConditionMixerControl(String id, int index, String description) {
            super(id, index, description);
        }

        @Override
        public boolean judge() {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //TODO, implement the real check here.
            return true;
        }
    }

    public PlaybackSpeakerCase(int categoryId, String caseName){

        this.CategoryId = categoryId;
        this.mCaseName = caseName;

        mConditions = new Vector<>();
    }

    @Override
    public boolean initConditions(String[] conditions) {

        for(int i=0; i<conditions.length; i++) {
            mConditions.add(new AudioConditionMixerControl(null,i,conditions[i]));
            if(DEBUG)
                Log.i(TAG, "    " + conditions[i]);
        }
        return true;
    }

    @Override
    public boolean run() {
        super.seek(1000);
        super.run();
        return true;
    }
}
