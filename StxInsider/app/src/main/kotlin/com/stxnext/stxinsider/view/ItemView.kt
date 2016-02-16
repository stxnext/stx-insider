package com.stxnext.stxinsider.view

import android.view.View

/**
 * Created by bkosarzycki on 16.02.16.
 */

interface ItemView {
    abstract fun <T>bind(item : T, position : Integer, clickListener: View.OnClickListener)
}