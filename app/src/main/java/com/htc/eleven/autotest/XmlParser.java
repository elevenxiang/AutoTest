package com.htc.eleven.autotest;

import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by eleven on 17-9-20.
 */

public class XmlParser {

    public static final String TAG = "XmlParser";

    private static boolean DEBUG = true;
    /**
     * get system line break;
     * */
    public static final String lineBreak = System.getProperty("line.separator");

    /**
     * copy assert file into external storage when Application was launched first time.
     * */
    private static void copyFileToExternalStorage(String sourceFile, File destFile) {

        try {

            InputStream assertFile = App.getApp().getAssets().open(sourceFile);
            InputStreamReader inputStreamReader = new InputStreamReader(assertFile);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            FileOutputStream outputStream = new FileOutputStream(destFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            String line;
            while ((line=bufferedReader.readLine()) != null) {
                bufferedWriter.append(line+lineBreak);
            }

            outputStream.flush();
            outputStreamWriter.flush();
            bufferedWriter.flush();

            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            bufferedReader.close();
            inputStreamReader.close();
            assertFile.close();


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
                    copyFileToExternalStorage(file,sdcard_file);
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
        if(DEBUG) {

        }

        String sdcard_file = GlobalConstants.path + File.separator + file;
        if(sdcard_file != null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);
                Element element = document.getDocumentElement();

                NodeList case_list = element.getElementsByTagName("Case");
                for (int i = 0; i < case_list.getLength(); i++) {
                    Element e1 = (Element) case_list.item(i);
                    if(DEBUG)
                        Log.i(TAG, e1.getAttribute("id") + ":" + e1.getAttribute("class"));

                    NodeList curCase = (NodeList) case_list.item(i);
                    for ( int j=0; j<curCase.getLength(); j++) {
                        Element e2 = (Element) curCase.item(i);
                        if(DEBUG)
                            Log.i(TAG, e2.getAttribute("id"));
                    }
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}