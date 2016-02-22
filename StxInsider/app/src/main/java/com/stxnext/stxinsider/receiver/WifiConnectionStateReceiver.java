package com.stxnext.stxinsider.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.stxnext.stxinsider.MainActivity;
import com.stxnext.stxinsider.MapActivity;

/**
 * Created by bkosarzycki on 04.02.16.
 */
public class WifiConnectionStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();

            WifiConnStateChangedListener listener = MapActivity.WifiStateListener.getWifiStateListener();
            if (listener != null)
                if (ssid != null && !ssid.contains("unknown"))
                    listener.stateChanged(ssid, true);
        }
    }
}
