package com.lalitp.zomatosampleapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by atulsia on 17/2/16.
 */
public class ZomatoSampleApp extends MultiDexApplication {

    private static ZomatoSampleApp sInstance;

    public static ZomatoSampleApp getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }



    public static Context context;


    @Override
    public void onCreate() {

        super.onCreate();
        sInstance = this;
        context = this;

    }



}
