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
                DetailsContentList(arrayOf(
                       DetailsContentListRow("Why to nearshore in Central Europe?", "10:30 - 11:00 - Wacław Zalewski & Henk van Leussen"),
                       DetailsContentListRow("Time to react!", "11:00 - 11:30 - Radosław Jankiewicz"),
                       DetailsContentListRow("Coffee break", "11:30 - 11:50"),
                       DetailsContentListRow("Behave automatically: (Almost) Effortless feature testing", "11:50 - 12:20 - Tomasz Muszczek & Piotr Błaszczyk"),
                       DetailsContentListRow("Is there a common pattern in fixing 'pain in the ass' projects?", "12:20 - 12:50 - Paweł Jurdeczka"),
                       DetailsContentListRow("iOS and Android Solutions", "12:50 - 13:00 - Rafał Gajewski"),
                       DetailsContentListRow("Lunch", "13:00 - 14:00"),
                       DetailsContentListRow("Experiences with small scale scrum scaling", "14:00 - 14:30 - Łukasz Aziukiewicz"),
                       DetailsContentListRow("What yo' mama ain't told ya about Promises - or - native browser Promises in JavaScript", "14:30 - 15:00 - Tomasz Maćkowiak"),
                       DetailsContentListRow("Coffee break", "15:00 - 15:20"),
                       DetailsContentListRow("DDD - the workflow for successful digital product creation", "15:20 - 15:50 - Dominik Oślizło"),
                       DetailsContentListRow("Software Quality Visualization", "15:50 - 16:20 - Łukasz Koczwara"),
                       DetailsContentListRow("STX Next - 2016 plans", "16:20 - 17:00 - Maciej Dziergwa"),
                       DetailsContentListRow("Break", "17:00 - 18:00"),
                       DetailsContentListRow("Banquet", "18:00 - 01:00")).toList())
            )
            val detailsItemString = Gson().toJson(detailsItem)

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("item", detailsItemString)
            intent.putExtra("type", DetailsActivity.TYPE.LIST.toString())
            startActivity(intent)
        }
        val adapter = SimpleItemListAdapter<ListItem, ListItemView<ListItem>>(R.layout.item_simple_list, bindFunc, onClickFunc, this);

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
