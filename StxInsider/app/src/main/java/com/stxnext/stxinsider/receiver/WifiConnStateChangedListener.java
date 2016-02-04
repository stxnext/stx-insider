package com.stxnext.stxinsider.receiver;

/**
 * Created by bkosarzycki on 04.02.16.
 */
public interface WifiConnStateChangedListener {
    void stateChanged(String ssid, boolean enabled);
}
