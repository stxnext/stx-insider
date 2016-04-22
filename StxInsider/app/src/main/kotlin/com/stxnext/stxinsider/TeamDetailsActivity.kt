package com.stxnext.stxinsider

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.google.gson.Gson
import com.stxnext.stxinsider.model.SliderItem
import butterknife.bindView
import com.stxnext.stxinsider.util.Util
import com.stxnext.stxinsider.util.addElevationAnimationWhenScroll
import com.stxnext.stxinsider.util.convertDpToPixel
import java.io.IOException

class TeamDetailsActivity : AppCompatActivity() {

    val mHeaderTextView: TextView by bindView(R.id.activity_team_details_team_header)
    val appBar: AppBarLayout by bindView(R.id.app_bar_layout)
    val mCollapsingToolbarLayout: CollapsingToolbarLayout by bindView(R.id.activity_details_collapsingToolbar)
    val mSubtitle: TextView by bindView(R.id.subtitle)
    val mDescriptionTextView: TextView by bindView(R.id.activity_team_details_team_description)
    val mBackgroundImageView: ImageView by bindView(R.id.activity_details_header_image)
    val mToolbar: Toolbar by bindView(R.id.toolbar)
    val header: LinearLayout by bindView(R.id.header)
    internal val TAG = TeamDetailsActivity::class.java.name

    var mTeam: SliderItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        setSupportActionBar(mToolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTeam = Gson().fromJson<SliderItem>(intent.getStringExtra("item"), SliderItem::class.java)
        bind(mTeam!!)
        addElevationAnimationWhenScroll(appBar, mCollapsingToolbarLayout, header)
    }

    private fun bind(item: SliderItem) {
        mHeaderTextView.text = item.header
        mDescriptionTextView.text = item.description
        if (item.category != null)
            mSubtitle.text = getString(item.category!!.intValue)
        try {
            val file = assets.open(item.imagePath)
            mBackgroundImageView.scaleType = ImageView.ScaleType.FIT_CENTER
            mBackgroundImageView.setImageDrawable(Drawable.createFromStream(file, null))
        } catch (e: IOException) {
            Log.e(TAG, "Error creating team image: " + e.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}
