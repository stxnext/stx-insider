package com.stxnext.stxinsider

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.gson.Gson
import com.stxnext.stxinsider.model.Team
import butterknife.bindView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.fragment.DetailsListFragment
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
        replaceContentFragment()
    }

    class EmptyFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater!!.inflate(R.layout.fragment_empty, container, false)
        }
    }

    private fun replaceContentFragment() {

        val detailsContentFragment = DetailsListFragment()

        //todo: change to dynamic type recognition
        val cont = Gson().fromJson(Gson().toJson( mItem!!.content), DetailsContentList::class.java)
        detailsContentFragment.itemData = cont

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activity_details_content_fragment, detailsContentFragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
