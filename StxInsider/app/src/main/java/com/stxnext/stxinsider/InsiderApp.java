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

        //removable appId, appToken - remove these from the cloud
        EstimoteSDK.initialize(getApplicationContext(), "bartosz-kosarzycki-stxnext-n24", "1b3d75bbb500490f335418aadd06f46b");
        EstimoteSDK.enableDebugLogging(true);
    }
}
