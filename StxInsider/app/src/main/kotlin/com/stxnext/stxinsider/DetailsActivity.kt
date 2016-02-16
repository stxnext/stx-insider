package com.stxnext.stxinsider

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView

import com.google.gson.Gson
import com.stxnext.stxinsider.model.Team
import butterknife.bindView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsItem

class DetailsActivity<T> : AppCompatActivity() {

    val mTitleTextView: TextView by bindView(R.id.activity_details_title)
    val mSubtitleTextView: TextView by bindView(R.id.activity_details_subtitle)

    var mItem: DetailsItem<T>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar!!.title = "Item details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mItem = Gson().fromJson<DetailsItem<T>>(intent.getStringExtra("item"), DetailsItem::class.java /*Team::class.java*/)
        bind(mItem!!)
    }

    private fun bind(item: DetailsItem<T>) {
        mTitleTextView.text = item.title
        mSubtitleTextView.text = item.subtitle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}
