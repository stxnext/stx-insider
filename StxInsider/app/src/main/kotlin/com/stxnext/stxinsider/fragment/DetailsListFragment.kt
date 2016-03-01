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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.google.gson.Gson
import com.stxnext.stxinsider.DetailsActivity
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.adapter.SimpleItemListAdapter
import com.stxnext.stxinsider.constant.EventsData2016
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsContentListRow
import com.stxnext.stxinsider.view.model.DetailsItem
import com.stxnext.stxinsider.view.model.ExtendedListItem

/**
 * Created by bkosarzycki on 16.02.16.
 */
class DetailsListFragment : Fragment() {

    var itemData: DetailsContentList? = null
    val recyclerView: RecyclerView by bindView(R.id.activity_details_content_list_recyclerview)
    private val marginDecoration = MarginDecoration(0)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_details_content_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        val itemsList = itemData?.data;

        val bindFunc = { baseView: FrameLayout, item: ExtendedListItem, position: Int, clickListener: View.OnClickListener ->
            val outerLayoutLL =  baseView.findViewById(R.id.item_extended_list_outer_layout) as LinearLayout
            val headerTextView = baseView.findViewById(R.id.item_extended_list_main_header) as TextView
            val bottomExtTextView = baseView.findViewById(R.id.item_extended_list_main_bottom_extension_text) as TextView
            headerTextView.text = item.title
            bottomExtTextView.text = item.subtitle

            outerLayoutLL.setOnClickListener { v: View -> baseView.performClick() }
            baseView.setOnClickListener(clickListener)
        }
        val onClickFunc = {position: Int?,v : View ->

            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("item", Gson().toJson(EventsData2016.data.get(position!!)))
            intent.putExtra("type", DetailsActivity.TYPE.STRING.toString())
            startActivity(intent)
        }

        val adapter = SimpleItemListAdapter<ExtendedListItem, ListItemView<ExtendedListItem>>(R.layout.item_extended_list, bindFunc, onClickFunc, activity,
                { ctx, attr -> ListItemView<ExtendedListItem>(R.layout.item_extended_list , bindFunc, context, null) });

        var  extItemsList : List<ExtendedListItem> = emptyList()
        itemsList!!.iterator().forEach { dcl : DetailsContentListRow -> extItemsList = extItemsList.plus(ExtendedListItem(dcl.header, dcl.additionalText))    }
        adapter.addItems(extItemsList)
        initializeRecyclerView(LinearLayoutManager(activity), adapter)
    }

    fun initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SimpleItemListAdapter<ExtendedListItem, ListItemView<ExtendedListItem>>) {
        //recyclerView.addItemDecoration(marginDecoration)
        //recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun onStop() {
        super.onPause()
        recyclerView.removeItemDecoration(marginDecoration)
    }
}