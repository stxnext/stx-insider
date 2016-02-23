@file:JvmName("ActivityUtil")
//@file:JvmMultifileClass
package com.stxnext.stxinsider.util

import android.app.Activity
import android.content.pm.PackageManager
import android.net.wifi.WifiManager

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