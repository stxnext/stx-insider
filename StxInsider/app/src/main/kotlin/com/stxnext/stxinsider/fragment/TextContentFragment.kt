package com.stxnext.stxinsider.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    var itemData: SpannableString? = null
    val mainTextView: TextView by bindView(R.id.activity_details_content_text_main_tv)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_details_content_text, container, false)
    }

    override fun onStart() {
        super.onStart()

        //val itemsList = itemData?.data;


        mainTextView.text = "TEMPORARY SPANNABLE TEXT"
    }
}