package com.htc.eleven.autotest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Vector;

public class CaseLoopService extends Service {

    public static final String TAG = "CaseLoopService";
    private static final boolean DEBUG = true;
    private Handler mHandlers;
    private LoopTestThread workThread = null;

    private Vector<Category> mCategories = null;

    public CaseLoopService() {
        mCategories = new Vector<>();
    }

    public class ServiceBinder extends Binder {

        public CaseLoopService getService(){
            return CaseLoopService.this;
        }
    }
    public void registerNotifier(Handler handler) {
        CaseLoopService.this.mHandlers = handler;
    }

    public void registerCategory(Category category){

        CaseLoopService.this.mCategories.add(category);
    }

    public void unRegisterCategory(Category category) {
        CaseLoopService.this.mCategories.remove(category);
    }

    public void start() {
        Log.i(TAG, "+++");

        workThread = new LoopTestThread(mHandlers, mCategories);
        workThread.start();

        Log.i(TAG, "---");
    }

    public void stop() {
        workThread.setRunning(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand() " + intent.toString() + ", flags = " + flags + ", startId =  " + startId);
        return super.onStartCommand(intent, flags, startId);
    }


    public class LoopTestThread extends Thread{

        private boolean mRunning = true;
        private Handler notifier;
        private Vector<Category> categories;

        public void setRunning(boolean running) {
            this.mRunning = running;
        }

        public void Notify(int id, Case caseId) {

            if(notifier != null) {
                Message msg = new Message();
                msg.what = id;

                Bundle bundle = new Bundle();
                bundle.putString("case", caseId.getCaseName());
                if(caseId.getErr() != null) {
                    if(DEBUG){
                        Log.i(TAG, caseId.toString() +": " + caseId.getErr().getDescription());
                    }
                    bundle.putString("con", caseId.getErr().getDescription());
                }
                bundle.putString("ret", caseId.getResult());

                msg.setData(bundle);
                notifier.sendMessage(msg);
            }
        }

        public void Notify(int id, Category categoryId) {

            if(notifier != null) {
                Message msg = new Message();
                msg.what = id;

                Bundle bundle = new Bundle();
                bundle.putInt("id", categoryId.getId());
                bundle.putString("category", categoryId.getCategoryName());
                msg.setData(bundle);
                notifier.sendMessage(msg);
            }
        }

        public LoopTestThread(Handler notifier, Vector<Category> categories) {
            this.notifier = notifier;
            this.categories = categories;
        }
        @Override
        public void run() {
            while (true) {
                for (Category c : categories) {
                    if(DEBUG) {
                        Log.i(TAG, "category: " +c.getId() + ": ==>" + c.getCategoryName());
                    }
                    Notify(MessageID.MESSAGE_CATEGORY,c);
                    for (Case item : c.getCases()) {
                        item.run();
                        Notify(MessageID.MESSAGE_CASE, item);

                        /**
                         * sleep 1 second to continue next.
                         * Not sure if it was really needed ?
                         * */
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(!mRunning) break;
                    }
                    Notify(MessageID.MESSAGE_CATEGORY_DONE,c);
                }

                /**
                 * break out after all test cases finished.
                 * */
                mRunning = false;
                Log.i(TAG, "All test cases finished !");
                break;
            }
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
