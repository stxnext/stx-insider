package com.stxnext.stxinsider.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.model.Team

/**
 * Created by bkosarzycki on 15.02.16.
 */

class ListItemView<T>(
        bindFuncParam : (baseView: FrameLayout, item: T, position: Integer, clickListener: View.OnClickListener) -> Unit,
        contextParam : Context?, attrs: AttributeSet?) : FrameLayout(contextParam, attrs) /*, ItemView */{

    val bindFunc = bindFuncParam
    var item: T? = null

    init {
        removeAllViews()
        addView(LayoutInflater.from(context).inflate(R.layout.item_simple_list_activity, this, false))
    }

    fun bind(item: T, position: Integer, clickListener: OnClickListener) {
        bindFunc.invoke(this, item, position, clickListener)
    }
}
