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
open abstract class BaseItemView(private val mContext: Context, attrs: AttributeSet?) : FrameLayout(mContext, attrs) {

    internal val TAG = BaseItemView::class.java.name
    var item: SliderItem? = null
        private set

    init {
        removeAllViews()
        addLayoutView(mContext);
    }

    abstract fun addLayoutView(cont: Context)

    @Suppress("UNUSED_PARAMETER")
    fun bind(item: SliderItem, position: Int, clickListener: OnClickListener?,
             seeMoreListener: OnTallItemViewClickListener?, itemClicked: Boolean) {
        this.item = item
        val nameTextView = findViewById(R.id.item_teams_list_header) as TextView
        val teamImageView = findViewById(R.id.item_teams_list_team_background) as ImageView
        //val titleTextView =  findViewById(R.id.title) as TextView;
        val description = findViewById(R.id.description) as TextView
        nameTextView.text = item.header

        try {
            val file = context.assets.open(item.imagePath)
            teamImageView.setImageDrawable(Drawable.createFromStream(file, null))
        } catch (e: IOException) {
            Log.e(TAG, "Error creating team image: " + e.toString())
        }

        if (clickListener != null && !this.javaClass.isAssignableFrom(TallItemView::class.java)) {
            this.setOnClickListener(clickListener)
            description.text = item.description
        } else {
            setUpTallItemView(description, item, itemClicked, position, seeMoreListener)
        }
    }

    private fun setUpTallItemView(description: TextView, item: SliderItem, itemClicked: Boolean, position: Int, seeMoreListener: OnTallItemViewClickListener?) {
        val portfolioLayout: ViewGroup = findViewById(R.id.portfolio_layout) as ViewGroup
        val seemoreDelimiter = findViewById(R.id.see_more_delimiter)
        val seemore = findViewById(R.id.see_more)
        if (itemClicked) {
            description.text = item.description
            deactivateSeeMore(seemore, seemoreDelimiter)
        } else {
            description.text = getShortenedString(item.description)
            activateSeeMore(seemore, seemoreDelimiter)
        }
        enableTransitionAnimations(portfolioLayout)
        seemore.setOnClickListener {
            Log.d(TAG, "see more clicked")
            description.text = item.description
            deactivateSeeMore(seemore, seemoreDelimiter)
            seeMoreListener?.onSeeMoreClick(position)
        }
    }

    private fun deactivateSeeMore(seemore: View, seemoreDelimiter: View) {
        seemore.visibility = GONE
        seemoreDelimiter.visibility = GONE
    }

    private fun activateSeeMore(seemore: View, seemoreDelimiter: View) {
        seemore.visibility = VISIBLE
        seemoreDelimiter.visibility = VISIBLE
    }

    fun getShortenedString(stringToShorten: String?): String? {
        val minimumLength: Int = 90;
        var shortenedString: String? = stringToShorten
        if (stringToShorten != null) {
            val shortenedStringEndIndex: Int = stringToShorten.indexOf(" ", minimumLength)
            if (shortenedStringEndIndex > -1) {
                shortenedString = stringToShorten.substring(0, shortenedStringEndIndex) + "..."
            }
        }
        return shortenedString
    }

    /**
     * Sets animations when there are changes inside layout.
     */
    private fun enableTransitionAnimations(view: ViewGroup) {
        val layoutTransition = LayoutTransition()
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        view.layoutTransition = layoutTransition
    }

    private fun disableTransitionAnimations(view: ViewGroup) {
        view.layoutTransition?.disableTransitionType(LayoutTransition.CHANGING)
    }

    interface OnTallItemViewClickListener {
        fun onSeeMoreClick(position: Int)
    }


}
