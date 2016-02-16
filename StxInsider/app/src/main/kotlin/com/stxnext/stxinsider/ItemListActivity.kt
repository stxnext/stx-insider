package com.stxnext.stxinsider

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import butterknife.bindView
import com.stxnext.stxinsider.adapter.SimpleItemListAdapter
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.MarginDecoration
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
        val itemsList = arrayOf(ListItem("One"), ListItem("Two"))
        val adapter = SimpleItemListAdapter<ListItem, ListItemView<ListItem>>(this);
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
