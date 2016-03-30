package com.stxnext.stxinsider.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.google.gson.Gson
import com.stxnext.stxinsider.DetailsActivity
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.adapter.SimpleItemListAdapter
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsContentListRow
import com.stxnext.stxinsider.view.model.DetailsItem
import com.stxnext.stxinsider.view.model.ExtendedListItem

/**
 * Created by bkosarzycki on 16.02.16.
 */
class TextContentFragment : Fragment() {

    enum class CONTENT_TYPE { SPANNABLE, HTML }

    var itemData: SpannableString? = null
    var contentType : CONTENT_TYPE? = null
    val mainTextView: TextView by bindView(R.id.activity_details_content_text_main_tv)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_details_content_text, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (contentType == null || contentType == CONTENT_TYPE.SPANNABLE)
            mainTextView.text = itemData
        else
            mainTextView.text = Html.fromHtml(itemData.toString())
    }
}