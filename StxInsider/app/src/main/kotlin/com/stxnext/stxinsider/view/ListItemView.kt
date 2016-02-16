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

class ListItemView<T>(contextParam : Context?, attrs: AttributeSet?) : FrameLayout(contextParam, attrs), ItemView {

    var item: T? = null

    init {
        removeAllViews()
        addView(LayoutInflater.from(context).inflate(R.layout.item_simple_list_activity, this, false))
    }

    override fun <T>bind(item: T, position: Integer, clickListener: OnClickListener) {
        val nameTextView = findViewById(R.id.item_simple_list_main_header) as TextView

        //todo: change to real impl
        nameTextView.text = "temporary"

        if (clickListener != null)
            this.setOnClickListener(clickListener)
    }
}
