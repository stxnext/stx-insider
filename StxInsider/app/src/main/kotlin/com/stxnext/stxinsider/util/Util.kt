package com.stxnext.stxinsider.util

import android.net.wifi.WifiManager

/**
 * Created by bkosarzycki on 22.02.16.
 */

fun WifiManager.getNetworkId(wifiManager: WifiManager, SSID: String): Int {
    val list = wifiManager.configuredNetworks
    if (list == null || list.size == 0)
        return -1

    for (i in list) {
        if (i.SSID != null && i.SSID == "\"" + SSID + "\"")
            return i.networkId
    }

    return -1
}