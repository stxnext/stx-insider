package com.stxnext.stxinsider;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

/**
 * Created by bkosarzycki on 04.02.16.
 */
public class InsiderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //EstimoteSDK.initialize(applicationContext, appId, appToken);
        EstimoteSDK.enableDebugLogging(true);
    }
}
