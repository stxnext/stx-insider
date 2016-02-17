package com.stxnext.stxinsider

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson
import com.stxnext.stxinsider.model.Team
import butterknife.bindView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.fragment.DetailsListFragment
import com.stxnext.stxinsider.fragment.TextContentFragment
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsItem

class DetailsActivity<T> : AppCompatActivity() {

    enum class TYPE { STRING, LIST, EMPTY }

    val mTitleTextView: TextView by bindView(R.id.activity_details_title)
    val mSubtitleTextView: TextView by bindView(R.id.activity_details_subtitle)

    var mItem: DetailsItem<T>? = null
    var mContentType : TYPE? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mItem = Gson().fromJson<DetailsItem<T>>(intent.getStringExtra("item"), DetailsItem::class.java)
        val contentTypeExtraString = intent.getStringExtra("type")

        mContentType = if (contentTypeExtraString != null)  DetailsActivity.TYPE.valueOf(contentTypeExtraString) else TYPE.EMPTY
        bind(mItem!!)

        if (mContentType == TYPE.EMPTY)
            Toast.makeText(this, "Null content found!", Toast.LENGTH_SHORT).show()
        else if (mContentType == TYPE.STRING) {
            Toast.makeText(this, "STRING!!!!", Toast.LENGTH_SHORT).show()
            replaceContentFragmentWithStringContent()
        } else if (mContentType == TYPE.LIST)
            replaceContentFragmentWithList()
        else
            Toast.makeText(this, "Content type unknown!", Toast.LENGTH_SHORT).show()
    }

    class EmptyFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater!!.inflate(R.layout.fragment_empty, container, false)
        }
    }

    private fun replaceContentFragmentWithStringContent() {
        val detailsContentFragment  = TextContentFragment()

        val content = Gson().fromJson(Gson().toJson( mItem!!.content), SpannableString::class.java)
        detailsContentFragment.itemData = content
        detailsContentFragment.contentType = TextContentFragment.CONTENT_TYPE.HTML

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activity_details_content_fragment, detailsContentFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun replaceContentFragmentWithList() {

        val detailsContentFragment = DetailsListFragment()

        val content = Gson().fromJson(Gson().toJson( mItem!!.content), DetailsContentList::class.java)
        detailsContentFragment.itemData = content

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
