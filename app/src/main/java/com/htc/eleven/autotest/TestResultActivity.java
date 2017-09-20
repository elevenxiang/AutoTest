package com.htc.eleven.autotest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TestResultActivity extends AppCompatActivity {

    private static final String TAG = "TestResultActivity";

    private boolean DEBUG = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_acitivy);

        Intent intent = getIntent();

        String [] data = intent.getStringArrayExtra(MessageID.FUNCTION_ID_DATA);

        for (String func: data) {

            if(DEBUG) {
                Log.i(TAG, func);
            }

            switch (func) {
                case GlobalConstants.AUDIO:
                    new AudioCategory(GlobalConstants.AUDIO,GlobalConstants.AUDIO_TEST_XML).Load();
                    break;
                case GlobalConstants.VIDEO:
                    new VideoCategory(GlobalConstants.VIDEO,GlobalConstants.VIDEO_TEST_XML).Load();
                    break;
            }
        }




    }
}
