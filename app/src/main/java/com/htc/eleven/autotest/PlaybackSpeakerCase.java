package com.htc.eleven.autotest;

import android.util.Log;

import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public class PlaybackSpeakerCase extends PlaybackCase {

    public static final String TAG = "PlaybackSpeakerCase";
    public static final boolean DEBUG = true;

    public PlaybackSpeakerCase(int categoryId, String caseName){

        this.CategoryId = categoryId;
        this.mCaseName = caseName;
        mConditions = new Vector<>();
    }

    @Override
    public boolean initConditions(String[] conditions) {
        for(int i=0; i<conditions.length; i++) {
            String[] strings = conditions[i].split("=");
            for (int j=0; j<strings.length; j++) {
                switch (strings[0]) {
                    case "mixer_control":
                        mConditions.add(new AudioConditionMixerControl(null, i, strings[1]));
                        break;
                    case "pcm_id":
                        mConditions.add(new AudioConditionPcmDevice(null, i, strings[1]));
                        break;
                }
            }
            if (DEBUG)
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
