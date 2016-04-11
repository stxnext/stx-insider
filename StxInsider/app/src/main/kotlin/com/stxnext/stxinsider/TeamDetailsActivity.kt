package com.stxnext.stxinsider

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

import com.google.gson.Gson
import com.stxnext.stxinsider.model.SliderItem
import butterknife.bindView
import com.stxnext.stxinsider.R
import java.io.IOException

class TeamDetailsActivity : AppCompatActivity() {

    val mHeaderTextView: TextView by bindView(R.id.activity_team_details_team_header)
    val mDescriptionTextView: TextView by bindView(R.id.activity_team_details_team_description)
    val mBackgroundImageView: ImageView by bindView(R.id.activity_details_header_image)
    val mToolbar: Toolbar by bindView(R.id.toolbar)
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
    }

    private fun bind(item: SliderItem) {
        mHeaderTextView.text = item.header
        mDescriptionTextView.text = item.description
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
