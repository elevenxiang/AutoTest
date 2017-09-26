package com.htc.eleven.autotest;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by eleven on 17-9-22.
 */

public class VoiceCallCase extends Case {

    public static final int CALL_HANDSET = 0;
    public static final int CALL_SPEAKER = 1;
    public static final int CALL_HEADSET = 2;
    public static final int CALL_BLUETOOTH = 3;

    private static final boolean DEBUG = true;
    private static final String TAG = "VoiceCallCase";

    private int callType;

    public class AudioConditionPhoneMode extends Condition {

        public AudioConditionPhoneMode(String id, int index, String description) {
            super(id, index, description);
        }

        @Override
        public boolean judge() {

            AudioManager mAudioManager =  (AudioManager) App.getApp().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.isBluetoothScoAvailableOffCall();
            switch (callType) {
                case CALL_HANDSET:
                    break;
                case CALL_SPEAKER:
                    mAudioManager.setSpeakerphoneOn(true);
                    break;
                case CALL_HEADSET:
                    //TODO, need add ways to switch to headset path.
                    break;
                case CALL_BLUETOOTH:
                    mAudioManager.setBluetoothScoOn(true);
                    break;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(mAudioManager.isSpeakerphoneOn())
                mAudioManager.setSpeakerphoneOn(false);
            if(mAudioManager.isBluetoothScoOn())
                mAudioManager.setBluetoothScoOn(false);

            //TODO, implement the real check here.
            if(mAudioManager.getMode() == AudioManager.MODE_IN_CALL) {
                return true;
            }
            else
                return false;
        }
    }

    private void startVoiceCall() {
        Uri uri = Uri.parse("tel:10086");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setAction(Intent.ACTION_CALL);
        App.getApp().getApplicationContext().startActivity(intent);
    }
    private void endVoiceCall() {
//        TelephonyManager telMag = (TelephonyManager) App.getApp().getSystemService(Context.TELEPHONY_SERVICE);
//        Class<TelephonyManager> c = TelephonyManager.class;
//        Method mthEndCall = null;
//        try {
//            mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
//            mthEndCall.setAccessible(true);
//
//            ITele
//            ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,
//                    (Object[]) null);
//            iTel.endCall();
//            LogOut.out(this, iTel.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
    }
    public VoiceCallCase(int categoryId, String caseName, int type) {

        this.callType = type;
        this.CategoryId = categoryId;
        this.mCaseName = caseName;

        mConditions = new Vector<>();
    }
    @Override
    public boolean run() {

        startVoiceCall();
        return true;
    }

    @Override
    public boolean initConditions(String[] conditions) {
        for(int i=0; i<conditions.length; i++) {
            mConditions.add(new AudioConditionPhoneMode(null,i,conditions[i]));
            if(DEBUG)
                Log.i(TAG, "    " + conditions[i]);
        }
        return true;
    }
}
