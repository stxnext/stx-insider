package com.stxnext.stxinsider.receiver

/**
 * Created by bkosarzycki on 04.02.16.
 */
interface WifiConnStateChangedListener {
    fun stateChanged(ssid: String, enabled: Boolean)
}
