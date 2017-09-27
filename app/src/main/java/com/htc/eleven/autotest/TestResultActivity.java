package com.htc.eleven.autotest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;

import java.io.IOException;

import static com.htc.eleven.autotest.XmlParser.lineBreak;

public class TestResultActivity extends AppCompatActivity {

    private static final String TAG = "TestResultActivity";

    private boolean DEBUG = true;

    private TextView view_output;
    public int curCategoryIdUnderTest = 0;

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

                    if(DEBUG)
                        Log.i(TAG, "get message from category: " + categoryId + ". " +category);

                    view_output.setText("");
                    App.getApp().getCacheTextResult().append("");
                    SpannableStringBuilder builder = new SpannableStringBuilder(category);
                    builder.setSpan(new ForegroundColorSpan(getColor(R.color.colorAccent)), 0, category.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    view_output.append(builder+lineBreak);
                    App.getApp().getCacheTextResult().append(builder+lineBreak);
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
                    caseDesc=bundle.getString("con");
                    ret=bundle.getString("ret");

                    if(ret.equals("Passed")) {
                        view_output.append("      [" + ret + "] " + "=====");
                        App.getApp().getCacheTextResult().append("      [" + ret + "] " + "=====");
                        view_output.append("  "+ caseName + lineBreak);
                        App.getApp().getCacheTextResult().append("  "+ caseName + lineBreak);
                    } else {
                        view_output.append("      [" + ret + "] " + "=====");
                        App.getApp().getCacheTextResult().append("      [" + ret + "] " + "=====");
                        view_output.append("  "+ caseName + ", Err =" + caseDesc + lineBreak);
                        App.getApp().getCacheTextResult().append("  "+ caseName + ", Err =" + caseDesc + lineBreak);
                        view_output.setTextColor(getResources().getColor(R.color.colorAccent,null));
                    }
                }
                    break;

                case MessageID.MESSAGE_CATEGORY_DONE:
                    break;
            }
        }

        @Override
        public void handleMessage(Message msg) {

            if(msg.what == MessageID.MESSAGE_XML_PARSER_DONE) {
                /**
                 * run test after parser xml files.
                 * */
                Log.i(TAG, "+++");
                App.getApp().start();
                Log.i(TAG, "---");
            } else
                placeViewFromMessage(msg);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_acitivy);

        view_output = (TextView) findViewById(R.id.text_output);

        findViewById(R.id.stopTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getApp().stop();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!App.getApp().UIActived) {

            try {
                Log.i(TAG, "start autotest_server +++");
                Runtime.getRuntime().exec("su 0 exec /system/bin/autotest_server");
                Log.i(TAG, "start autotest_server ---");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(App.getApp().init_check()) {
                App.getApp().registerServiceNotifier(myHandler);
            } else {
                Toast.makeText(TestResultActivity.this, "目前发现绑定服务还没有成功!", Toast.LENGTH_SHORT).show();
                finish();
            }
            Intent intent = getIntent();
            String [] data = intent.getStringArrayExtra(MessageID.FUNCTION_ID_DATA);
            new xmlThread(myHandler, data).start();
            App.getApp().UIActived = true;
        }
    }

    public class xmlThread extends Thread  {

        private Handler handler;
        private String [] data;
        public xmlThread(Handler handler, String[] data) {
            this.handler = handler;
            this.data = data;
        }

        @Override
        public void run() {
            parserXmlFiles(data);
            handler.sendEmptyMessage(MessageID.MESSAGE_XML_PARSER_DONE);
        }
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
        App.getApp().stop();
        App.getApp().clear();
    }
}
