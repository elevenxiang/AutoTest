package com.htc.eleven.autotest;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Vector;

/**
 * Created by eleven on 17-9-19.
 */

public class App extends Application {

    private static final String TAG = "AutoTest-App";
    private static App mApp;

    private Intent intentSrv = null;
    private CaseLoopConnection myConnection;
    private CaseLoopService.ServiceBinder mProxy = null;

    private boolean initialized = false;

    public boolean init_check() {
        return initialized;
    }


    public class CaseLoopConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mProxy = (CaseLoopService.ServiceBinder) iBinder;

            initialized = true;

            Log.i(TAG, "Connect to CaseLoopService Successfully !");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            initialized = false;
            Log.i(TAG, "Disconnect to CaseLoopService Successfully ! : " + componentName.toString());

        }
    }

    public void registerServiceNotifier(Handler handler) {
        if(mProxy != null) {
            mProxy.getService().registerNotifier(handler);
        }
    }

    public void registerCategory(Category category){

        if(mProxy != null) {
            mProxy.getService().registerCategory(category);
        }
    }

    public void unRegisterCategory(Category category) {
        if(mProxy != null) {
            mProxy.getService().unRegisterCategory(category);
        }
    }

    public void clear() {
        if(mProxy != null) {
            mProxy.getService().clear();
        }
    }

    public void start() {
        if(mProxy != null) {
            mProxy.getService().start();
        }
    }

    public void stop() {
        if(mProxy != null) {
            mProxy.getService().stop();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "App Created !");

        mApp = (App) getApplicationContext();

        init();

    }

    public void init() {

        myConnection = new CaseLoopConnection();
        intentSrv = new Intent(getApplicationContext(), CaseLoopService.class);
        bindService(intentSrv,myConnection, Context.BIND_AUTO_CREATE);

    }

    Vector<Category> GlobalCategory;

//    public Vector<Category> getGlobalCategory() {
//        return GlobalCategory;
//    }

    public static App getApp() {
        return mApp;
    }

}
