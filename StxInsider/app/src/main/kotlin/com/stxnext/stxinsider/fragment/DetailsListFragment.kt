package com.stxnext.stxinsider.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.bindView
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.adapter.SimpleItemListAdapter
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsContentListRow
import com.stxnext.stxinsider.view.model.ExtendedListItem

/**
 * Created by bkosarzycki on 16.02.16.
 */
class DetailsListFragment : Fragment() {

    var itemData: DetailsContentList? = null
    val recyclerView: RecyclerView by bindView(R.id.activity_details_content_list_recyclerview)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_details_content_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        val itemsList = itemData?.data;

        val bindFunc = { baseView: FrameLayout, item: ExtendedListItem, position: Integer, clickListener: View.OnClickListener ->
//            val nameTextView = baseView.findViewById(R.id.item_simple_list_main_header) as TextView
//            nameTextView.text = item.title

            if (clickListener != null)
                baseView.setOnClickListener(clickListener)
        }
        val onClickFunc = {v : View ->
            Toast.makeText(activity, "Clicked!", Toast.LENGTH_SHORT).show()
        }

        val adapter = SimpleItemListAdapter<ExtendedListItem, ListItemView<ExtendedListItem>>(R.layout.item_simple_list_activity, bindFunc, onClickFunc, activity);

        var  extItemsList : List<ExtendedListItem> = emptyList()
        itemsList!!.iterator().forEach { dcl : DetailsContentListRow -> extItemsList = extItemsList.plus(ExtendedListItem(dcl.header, dcl.additionalText))    }
        adapter.addItems(extItemsList)
        initializeRecyclerView(LinearLayoutManager(activity), adapter)
    }

    fun initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SimpleItemListAdapter<ExtendedListItem, ListItemView<ExtendedListItem>>) {
        recyclerView.addItemDecoration(MarginDecoration(20))
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}