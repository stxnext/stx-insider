package com.stxnext.stxinsider.view.elementItemView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.stxnext.stxinsider.R

/**
 * Created by bkosarzycki on 25.02.16.
 */
class ShortItemView(private val mContext: Context, attrs: AttributeSet?, viewType: Int) : BaseItemView(mContext, attrs, viewType) {

    override fun addLayoutView(cont : Context, viewType: Int) {
        addView(LayoutInflater.from(cont).inflate(R.layout.item_team_list, this, false))
    }
}