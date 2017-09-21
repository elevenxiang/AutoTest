package com.htc.eleven.autotest;

import android.os.Environment;

import java.io.File;

/**
 * Created by eleven on 17-9-20.
 */

public class GlobalConstants {

    public static final String AUDIO = "Audio";
    public static final String VIDEO = "video";
    public static final String CAMERA = "camera";
    public static final String DISPLAY = "display";
    public static final String FINGERPRINT = "fingerprint";
    public static final String SENSOR = "sensor";
    public static final String USB = "usb";

    public static final int AUDIO_ID = 0;
    public static final int VIDEO_ID = 1;
    public static final int CAMERA_ID = 2;
    public static final int DISPLAY_ID = 3;
    public static final int FINGERPRINT_ID = 4;
    public static final int SENSOR_ID = 5;
    public static final int USB_ID = 6;

    public static final int TEST_NUM = USB_ID + 1;


    public static String app_folder = "AutoTest";
    public static String xml_folder = "xmls";

    public static String path = Environment.getExternalStorageDirectory() + File.separator + app_folder + File.separator + xml_folder;

    public static String AUDIO_TEST_XML = "Audio_Test.xml";
    public static String VIDEO_TEST_XML = "Video_Test.xml";
    public static String CAMERA_TEST_XML = "Camera_Test.xml";
    public static String DISPLAY_TEST_XML = "Display_Test.xml";
    public static String FINGERPRINT_TEST_XML = "Fingerprint_Test.xml";
    public static String SENSOR_TEST_XML = "Sensor_Test.xml";
    public static String USB_TEST_XML = "Usb_Test.xml";

}
