package com.stxnext.stxinsider.view.elementItemView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.model.SliderItem
import java.io.IOException

/**
 * Created by bkosarzycki on 25.02.16.
 */
class ShortItemView(private val mContext: Context, attrs: AttributeSet?, viewType: Int) : BaseItemView(mContext, attrs, viewType) {

    override fun addLayoutView(cont : Context, viewType: Int) {
        if (isHeader(viewType)) {
            addView(LayoutInflater.from(cont).inflate(R.layout.item_team_header, this, false))
        } else {
            addView(LayoutInflater.from(cont).inflate(R.layout.item_team_list, this, false))
        }
    }

    @Suppress("UNUSED_PARAMETER")
    override fun bind(item: SliderItem, position: Int, clickListener: OnClickListener?,
                      seeMoreListener: OnItemViewSeeMoreClickListener?, itemClicked: Boolean) {
        this.item = item
        if (position == 0) {
            if (item.description != null)
                (findViewById(R.id.fragment_team_header_footer) as TextView).text = item.description
            if (item.header != null) {
                var headerString = findViewById(R.id.fragment_team_header_related_project_textview) as TextView
                headerString.text = item.header
            }
            if (item.imageResource != null) {
                val headerImage = findViewById(R.id.header_image) as ImageView
                headerImage.setImageResource(item.imageResource!!)
            }
        } else {
            val nameTextView = findViewById(R.id.item_teams_list_header) as TextView
            val teamImageView = findViewById(R.id.item_teams_list_team_background) as ImageView
            //val titleTextView =  findViewById(R.id.title) as TextView;
            val description = findViewById(R.id.description) as TextView
            nameTextView.text = item.header
            setImage(item, teamImageView)
            description.text = item.description
            if (clickListener != null) {
                this.setOnClickListener(clickListener)
            }
        }
    }

    private fun isHeader(viewType: Int) = viewType == 0

    private fun setImage(item: SliderItem, teamImageView: ImageView) {
        try {
            val file = context.assets.open(item.imagePath)
            teamImageView.setImageDrawable(Drawable.createFromStream(file, null))
        } catch (e: IOException) {
            Log.e(TAG, "Error creating team image: " + e.toString())
        }
    }
}