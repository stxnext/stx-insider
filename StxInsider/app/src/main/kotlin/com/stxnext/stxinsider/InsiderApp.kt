package com.stxnext.stxinsider

import android.app.Application
import com.estimote.sdk.EstimoteSDK
import com.stxnext.stxinsider.inject.DaggerMainComponent
import com.stxnext.stxinsider.inject.MainAppModule
import com.stxnext.stxinsider.inject.MainComponent

/**
 * Created by bkosarzycki on 01.03.16.
 */
class InsiderApp : Application() {

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var component: MainComponent
    }


    override fun onCreate() {
        super.onCreate()

        component = DaggerMainComponent
                .builder()
                .mainAppModule(MainAppModule(getApplicationContext()))
                .build();
        //component.inject(this) //if you want to inject to InsiderApp

        //removable appId, appToken - remove these from the cloud
        EstimoteSDK.initialize(applicationContext, "bartosz-kosarzycki-stxnext-n24", "1b3d75bbb500490f335418aadd06f46b")
        EstimoteSDK.enableDebugLogging(true)
    }
}