package com.htc.eleven.autotest;

import android.util.Log;

import java.util.Vector;

/**
 * Created by eleven on 17-9-20.
 */

public class AudioCategory extends Category {

    private static final String TAG = "AudioCategory";
    private static final boolean DEBUG = true;
    private String file;

    private static final String PLAYBACK_SPEAKER = "playback_speaker";
    private static final String PLAYBACK_HEADSET = "playback_headset";
    private static final String PLAYBACK_A2DP = "playback_a2dp";

    private static final String VOICE_CALL_EARPIECE = "voice_call_earpiece";
    private static final String VOICE_CALL_SPEAKER = "voice_call_speaker";
    private static final String VOICE_CALL_HEADSET = "voice_call_headset";
    private static final String VOICE_CALL_BLUETOOTH = "voice_call_bluetooth";

    public AudioCategory(int id, String name, String file) {
        this.id = id;
        this.mCategoryName = name;
        this.file = file;
        mCases = new Vector<>();
    }

    public boolean Load() {
        initXml(file);
        App.getApp().registerCategory(this);
        return true;
    }

    public boolean initXml(String file) {
        return XmlParser.XmlParser(file, this);
    }

    public void insertCase(int categoryID, String name, String[] conditions) {

        Case audioCase = null;

        switch (name) {
            case PLAYBACK_SPEAKER:
                audioCase = new PlaybackSpeakerCase(categoryID, name);
                break;
            case PLAYBACK_HEADSET:
                audioCase = new PlaybackHeadsetCase(categoryID, name);
                break;
            case PLAYBACK_A2DP:
                audioCase = new PlaybackA2DPCase(categoryID, name);
                break;
            case VOICE_CALL_EARPIECE:
                audioCase = new VoiceCallCase(categoryID, name, VoiceCallCase.CALL_HANDSET);
                break;
            case VOICE_CALL_SPEAKER:
                audioCase = new VoiceCallCase(categoryID, name, VoiceCallCase.CALL_SPEAKER);
                break;
            case VOICE_CALL_HEADSET:
                audioCase = new VoiceCallCase(categoryID, name, VoiceCallCase.CALL_HEADSET);
                break;
            case VOICE_CALL_BLUETOOTH:
                audioCase = new VoiceCallCase(categoryID, name, VoiceCallCase.CALL_BLUETOOTH);
                break;
        }

        if(audioCase != null) {
            if(DEBUG)
                Log.i(TAG, audioCase.mCaseName + ":");
            audioCase.initConditions(conditions);
            mCases.add(audioCase);
        }
    }

}
