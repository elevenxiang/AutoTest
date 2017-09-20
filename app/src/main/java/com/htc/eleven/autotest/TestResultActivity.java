package com.htc.eleven.autotest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;

import static com.htc.eleven.autotest.XmlParser.lineBreak;

public class TestResultActivity extends AppCompatActivity {

    private static final String TAG = "TestResultActivity";

    private boolean DEBUG = true;

    private LinearLayout result_layout;
    private TextView[] views = new TextView[GlobalConstants.TEST_NUM];
    private int curCategoryIdUnderTest = 0;

    private resultHandler myHandler = new resultHandler();
    private class resultHandler extends Handler {

        private void placeViewFromMessage(Message msg) {

            if(DEBUG) {
                Log.i(TAG, msg.toString());
            }
            switch (msg.what) {
                case MessageID.MESSAGE_CATEGORY:
                {
                    String category;
                    int categoryId;
                    Bundle bundle = msg.getData();

                    categoryId = bundle.getInt("id");
                    category=bundle.getString("category");

                    views[categoryId].append(category+lineBreak);
                    curCategoryIdUnderTest = categoryId;


                }
                    break;
                case MessageID.MESSAGE_CASE:
                {
                    String caseName;
                    String caseDesc;
                    String ret;

                    Bundle bundle = msg.getData();

                    caseName=bundle.getString("case");
                    caseDesc=bundle.getString("conn");
                    ret=bundle.getString("ret");

                    views[curCategoryIdUnderTest].append("  "+ caseName);
                    if(ret.equals("Passed")) {
                        views[curCategoryIdUnderTest].append("====="+ "[" + ret + "]" + lineBreak);
                    } else {
                        views[curCategoryIdUnderTest].append("====="+ "[" + ret + "]" + lineBreak);
                        views[curCategoryIdUnderTest].append("      " + caseDesc);
                        views[curCategoryIdUnderTest].setTextColor(getResources().getColor(R.color.colorAccent,null));
                    }
                }
                    break;

                case MessageID.MESSAGE_CATEGORY_DONE:
                    result_layout.addView(views[curCategoryIdUnderTest]);
                    break;
            }
        }

        @Override
        public void handleMessage(Message msg) {
            placeViewFromMessage(msg);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_acitivy);

        result_layout = (LinearLayout) findViewById(R.id.show_result_layout);

        if(App.getApp().init_check()) {
            App.getApp().registerServiceNotifier(myHandler);
        } else {
            Toast.makeText(TestResultActivity.this, "目前发现绑定服务还没有成功!", Toast.LENGTH_SHORT).show();
            finish();
        }
        Intent intent = getIntent();

        String [] data = intent.getStringArrayExtra(MessageID.FUNCTION_ID_DATA);

        new AsyncTask<String[], Void, String>() {

            @Override
            protected String doInBackground(String[]... strings) {

                parserXmlFiles(strings[0]);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                /**
                 * run test after parser xml files.
                 * */
                App.getApp().start();
            }
        }.execute(data);

    }

    private void parserXmlFiles(String[] data) {

        for (String func: data) {

            if(func != null) {
                if (DEBUG) {
                    Log.i(TAG, func);
                }

                switch (func) {
                    case GlobalConstants.AUDIO:
                        new AudioCategory(GlobalConstants.AUDIO_ID,GlobalConstants.AUDIO, GlobalConstants.AUDIO_TEST_XML).Load();
                        break;
                    case GlobalConstants.VIDEO:
                        new VideoCategory(GlobalConstants.VIDEO_ID,GlobalConstants.VIDEO, GlobalConstants.VIDEO_TEST_XML).Load();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
