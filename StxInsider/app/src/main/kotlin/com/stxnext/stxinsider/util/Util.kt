@file:JvmName("ActivityUtil")
//@file:JvmMultifileClass
package com.stxnext.stxinsider.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Created by bkosarzycki on 22.02.16.
 */

class Util

fun WifiManager.getNetworkId(wifiManager: WifiManager, SSID: String): Int {
    val list = wifiManager.configuredNetworks
    if (list == null || list.size == 0)
        return -1

    for (i in list)
        if (i.SSID != null && i.SSID == "\"" + SSID + "\"")
            return i.networkId
    return -1
}


fun Activity.getAppVersion(activity: Activity): String {
    try {
        val manager = activity.packageManager
        return manager.getPackageInfo(activity.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) { /* ignore */ }
    return "0.0.0"
}

fun OkHttpClient.isDeviceOnline(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

//fun Drawable.loadImageDrawable() {
//    try {
//        val file = context.assets.open(teamCategoryHeader.imagePath)
//        val d = Drawable.createFromStream(file, null)
//        img.setImageDrawable(d)
//
//        if (teamCategoryHeader.background != null && !teamCategoryHeader.background!!.isEmpty()) {
//            val backgFile = context.assets.open(teamCategoryHeader.background)
//            val backDraw = Drawable.createFromStream(backgFile, null)
//            outerLL.background = backDraw
//        }
//    } catch (e: IOException) {
//        Log.e(TAG, "Cannot read image from assets: " + e.toString())
//    }
//}