package com.stxnext.stxinsider.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

import com.stxnext.stxinsider.MainActivity
import com.stxnext.stxinsider.MapActivity

/**
 * Created by bkosarzycki on 04.02.16.
 */
class WifiConnectionStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val info = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
        if (info != null && info.isConnected) {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ssid = wifiInfo.ssid

            val listener = MapActivity.wifiStateListener
            if (listener != null)
                if (ssid != null && !ssid.contains("unknown"))
                    listener.stateChanged(ssid, true)
        }
    }
}
