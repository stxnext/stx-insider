package com.stxnext.stxinsider

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.google.gson.Gson
import com.stxnext.stxinsider.adapter.SimpleItemListAdapter
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.model.DetailsContentList
import com.stxnext.stxinsider.view.model.DetailsContentListRow
import com.stxnext.stxinsider.view.model.DetailsItem
import com.stxnext.stxinsider.view.model.ListItem

class ItemListActivity : AppCompatActivity() {

    val recyclerView: RecyclerView by bindView(R.id.activity_item_list_recyclerview)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val title = intent.getStringExtra("title")
        if (title != null && !title.isEmpty())
            supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initializeList()

    }

    private fun initializeList() {
        val itemsList = arrayOf(ListItem("2nd STX Next Summit"))

        val bindFunc = { baseView: FrameLayout, item: ListItem, position: Integer, clickListener: View.OnClickListener ->
            val nameTextView = baseView.findViewById(R.id.item_simple_list_main_header) as TextView
            nameTextView.text = item.title

            if (clickListener != null)
                baseView.setOnClickListener(clickListener)
        }
        val onClickFunc = {v : View ->

            //todo: get item from that view and get the content string
            val detailsItem = DetailsItem<DetailsContentList>("2nd STX Next Summit", "Schedule",
                DetailsContentList(arrayOf(DetailsContentListRow("Why to nearshore in Central Europe?", "10:30 - 11:00"),
                                           DetailsContentListRow("Time to react!", "11:00 - 11:30"),
                                           DetailsContentListRow("Coffee break", "11:30 - 11:50")).toList())
            )
            val detailsItemString = Gson().toJson(detailsItem)

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("item", detailsItemString)
            startActivity(intent)
            Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show()
        }
        val adapter = SimpleItemListAdapter<ListItem, ListItemView<ListItem>>(R.layout.item_simple_list_activity, bindFunc, onClickFunc, this);

        adapter.addItems(itemsList.toList())
        recyclerView.adapter = adapter;
        initializeRecyclerView(LinearLayoutManager(baseContext), adapter);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    fun <T, TView : View?>initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SimpleItemListAdapter<T, TView>) {
        recyclerView.addItemDecoration(MarginDecoration(20))
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}
