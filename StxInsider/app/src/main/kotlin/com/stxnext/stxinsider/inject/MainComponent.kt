package com.stxnext.stxinsider.inject

import com.stxnext.stxinsider.InsiderApp
import com.stxnext.stxinsider.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bkosarzycki on 01.03.16.
 */

@Singleton
@Component(modules = arrayOf(MainAppModule::class, NetworkModule::class))
interface MainComponent {
    fun inject(application: InsiderApp)
    fun inject(mainActivity: MainActivity)
}