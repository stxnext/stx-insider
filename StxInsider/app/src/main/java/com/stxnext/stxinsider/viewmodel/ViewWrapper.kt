package com.stxnext.stxinsider.viewmodel

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by bkosarzycki on 12/29/15.
 */
class ViewWrapper<V : View>(val view: V) : RecyclerView.ViewHolder(view)
