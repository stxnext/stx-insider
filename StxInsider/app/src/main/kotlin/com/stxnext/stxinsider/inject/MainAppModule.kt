package com.stxnext.stxinsider.inject

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bkosarzycki on 01.03.16.
 */
@Module
class MainAppModule(private val context: Context) {
    private val PREF_NAME = "prefs"

    @Provides
    fun context(): Context {
        return context
    }

    /**
     * Provides Android SharedPreferences for the entire app using Dagger2's injection.
     */
    @Provides
    @Singleton
    fun provideAppPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}