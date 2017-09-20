package com.htc.eleven.autotest;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by eleven on 17-9-20.
 */

public class XmlParser {

    public static final String TAG = "XmlParser";

    /**
     * copy assert file into external storage when Application was launched first time.
     * */
    private static void copyResultFileToExternalStorage(InputStream assertFile, FileOutputStream outputStream) {

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(assertFile);
            char[] data = new char[assertFile.available()];
            inputStreamReader.read(data);
            inputStreamReader.close();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean CopyToSdcard( String file) {
        /**
         * get file location, and judge if it exist.
         * */

        File folder = new File(GlobalConstants.path);

        if(!folder.exists()) {
            Log.i(TAG, "Try to Create folder: " + GlobalConstants.path);
            if (!folder.mkdirs()) {
                Toast.makeText(App.getApp().getApplicationContext(), "创建XML文件目录失败 !", Toast.LENGTH_SHORT).show();
                return false;
            }

            Log.i(TAG, "Create xml folder in sdcard successfully !");
        }
        File  sdcard_file = new File(GlobalConstants.path + File.separator + file);

        Log.i(TAG,String.format("Create %s !", sdcard_file.getAbsolutePath()));
        if(!sdcard_file.exists()) {
            try {
                if(!sdcard_file.createNewFile()) {
                    Log.e(TAG, "Create file: " + sdcard_file.getAbsolutePath() + " " + "Failed !");
                    return false;
                } else {
                    Log.i(TAG, "Create file: "+sdcard_file.getAbsolutePath()+" Successfully !");
                    InputStream assertFile = App.getApp().getAssets().open(file);
                    FileOutputStream outputStream = new FileOutputStream(file);

                    copyResultFileToExternalStorage(assertFile,outputStream);

                    outputStream.flush();
                    assertFile.close();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * static function to parser xml and load data into function category.
     * */
    public static boolean XmlParser(String file, Category category) {

        boolean ret;
        ret = CopyToSdcard(file);
        if(!ret) {
            Log.e(TAG, "XmlParser failed !");
            return ret;
        }

        //TODO, here we need parser xml and save database into category.

        return true;
    }
}
