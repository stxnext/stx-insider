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

class ListItemView(contextParam : Context?, attrs: AttributeSet?) : FrameLayout(contextParam, attrs) {

    var item: Team? = null

    init {
        removeAllViews()
        addView(LayoutInflater.from(context).inflate(R.layout.item_simple_list_activity, this, false))
    }

    fun bind(item : Team, position : Integer, clickListener: View.OnClickListener ) {

        val nameTextView = findViewById(R.id.item_teams_list_header) as TextView

        if (clickListener != null)
            this.setOnClickListener(clickListener)
    }
}
