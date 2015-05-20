package com.wazabe.folklore.guindailles;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 201601 on 5/20/2015.
 */
public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}