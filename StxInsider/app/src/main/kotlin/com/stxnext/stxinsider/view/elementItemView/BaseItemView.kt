package com.stxnext.stxinsider.view.elementItemView

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.model.SliderItem

import java.io.IOException
import java.io.InputStream

/**
 * Created by bkosarzycki on 01.02.16.
 */
open abstract class BaseItemView(private val mContext: Context, attrs: AttributeSet?, viewType: Int) : FrameLayout(mContext, attrs) {

    internal val TAG = BaseItemView::class.java.name
    var item: SliderItem? = null

    init {
        removeAllViews()
        addLayoutView(mContext, viewType);
    }

    abstract fun addLayoutView(cont: Context, viewType: Int)

    @Suppress("UNUSED_PARAMETER")
    abstract fun bind(item: SliderItem, position: Int, clickListener: OnClickListener?,
                      seeMoreListener: OnItemViewSeeMoreClickListener?, itemClicked: Boolean)

    interface OnItemViewSeeMoreClickListener {
        fun onSeeMoreClick(position: Int)
    }

}
