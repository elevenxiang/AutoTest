package com.htc.eleven.autotest;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private Button BtnStart;
    private CheckBox cb_audio;
    private CheckBox cb_video;
    private CheckBox cb_camera;
    private CheckBox cb_display;
    private CheckBox cb_fingerprint;
    private CheckBox cb_sensor;
    private CheckBox cb_usb;


    /**
     * permission strings for read/write external storage.
     * */
    private static final String[] mPermissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"};

    /**
     * request code & result.
     * */
    private static final int mRequestCode = 1;
    private static final int mRequestSuccessfully = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");
        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.result_tv);
//        tv.setText(stringFromJNI());

        /**
         * request permission.
         * */
        requestPermissions(mPermissions,mRequestCode);

        cb_audio = (CheckBox) findViewById(R.id.cb_audio);
        cb_video = (CheckBox) findViewById(R.id.cb_video);
        cb_camera = (CheckBox) findViewById(R.id.cb_camera);
        cb_display = (CheckBox) findViewById(R.id.cb_display);
        cb_fingerprint = (CheckBox) findViewById(R.id.cb_fingerprint);
        cb_sensor = (CheckBox) findViewById(R.id.cb_sensor);
        cb_usb = (CheckBox) findViewById(R.id.cb_usb);

        BtnStart = (Button) findViewById(R.id.startTestBtn);
        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] functions = new String[GlobalConstants.TEST_NUM];


                if(cb_audio.isChecked()) functions[GlobalConstants.AUDIO_ID] = GlobalConstants.AUDIO;
                if(cb_video.isChecked()) functions[GlobalConstants.VIDEO_ID] = GlobalConstants.VIDEO;
                if(cb_camera.isChecked()) functions[GlobalConstants.CAMERA_ID]= GlobalConstants.CAMERA;
                if(cb_display.isChecked()) functions[GlobalConstants.DISPLAY_ID] = GlobalConstants.DISPLAY;
                if(cb_fingerprint.isChecked()) functions[GlobalConstants.FINGERPRINT_ID] = GlobalConstants.FINGERPRINT;
                if(cb_sensor.isChecked()) functions[GlobalConstants.SENSOR_ID] = GlobalConstants.SENSOR;
                if(cb_usb.isChecked()) functions[GlobalConstants.USB_ID] = GlobalConstants.USB;

                Intent intent = new Intent(MainActivity.this, TestResultActivity.class);
                intent.putExtra(MessageID.FUNCTION_ID_DATA, functions);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == mRequestCode) {
            for (int i=0; i<grantResults.length; i++) {
                if(grantResults[i] != mRequestSuccessfully)
                    finish(); // if user reject allow permissions, just exit directly.
            }
        }

        Log.i(TAG, "onRequestPermissionsResult Successfully !");
    }

}
