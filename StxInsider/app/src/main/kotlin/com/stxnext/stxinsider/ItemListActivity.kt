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
import com.stxnext.stxinsider.view.SidesMarginDecoration
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
//        recyclerView.postDelayed(Runnable { recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick()  } , 10);
    }

    private fun initializeList() {
        val itemsList = arrayOf(ListItem("1st STX Next Tech Summit"), ListItem("2nd STX Next Summit"))

        val bindFunc = { baseView: FrameLayout, item: ListItem, position: Int, clickListener: View.OnClickListener ->
            val nameTextView = baseView.findViewById(R.id.item_simple_list_main_header) as TextView
            nameTextView.text = item.title

            baseView.setOnClickListener(clickListener)
        }
        val onClickFunc = {position: Int? ,v : View ->

            var detailsItem : DetailsItem<DetailsContentList>? = null;
            //todo: get item from that view and get the content string
            if (position == 0)
                detailsItem = DetailsItem<DetailsContentList>(title = "1st STX Next Tech Summit",  subtitle = "21 March 2015", content =
                DetailsContentList(arrayOf(
                        DetailsContentListRow("Natural Language Processing in Python (with a twist of machine learning, perhaps)", "10:30 - 11:10 - Tomasz Kuczmarski"),
                        DetailsContentListRow("How to build a competitor to Sublime, Vim and Emacs using HTML", "11:10 - 11:50 - Zef Hemel"),
                        DetailsContentListRow("Coffee break", "11:50 - 12:05"),
                        DetailsContentListRow("Dealing with large-scale JavaScript application architecture", "12:05 - 12:45 - Michał Janiszewski, Michał Maćkowiak, Radosław Małecki"),
                        DetailsContentListRow("Creating wearable apps with Android Wear", "12:45 - 13:20 - Tomasz Konieczny"),
                        DetailsContentListRow("Breaking backwards compatibility: The easy way", "13:20 - 14:00 - Flavio Premoli"),
                        DetailsContentListRow("Lunch", "14:00 - 15:00"),
                        DetailsContentListRow("A new management hype? Holacracy: its benefits and challenges", "15:00 - 15:40 - Ruben Timmerman"),
                        DetailsContentListRow("The forgotten promise of agile development", "15:40 - 16:20 - Jacek Wieczorek"),
                        DetailsContentListRow("My personal tech-writing Agile Manifesto", "16:20 - 16:50 - Mikey Ariel"),
                        DetailsContentListRow("Coffee break", "16:50 - 17:05"),
                        DetailsContentListRow("Cloud services beyond infrastructure and OpenStack", "17:45 - 18:25 - Flavio Premoli"),
                        DetailsContentListRow("Key Note: Self-organizing teams - Team Maturity", "16:20 - 17:00 - Angel Medinilla"),
                        DetailsContentListRow("10 years Anniversary talk", "18:25 - 19:00 - Maciej Dziergwa"),
                        DetailsContentListRow("Break", "19:00 - 20:00"),
                        DetailsContentListRow("Banquet", "20:00 - 02:00")).toList(), 1), replacingImagePath = null
            )
            else if (position == 1)

                detailsItem = DetailsItem<DetailsContentList>(title = "2nd STX Next Summit",  subtitle = "Schedule", content =
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
                       DetailsContentListRow("Banquet", "18:00 - 01:00")).toList(), 2), replacingImagePath = null
                )
            if (detailsItem != null) {
                val detailsItemString = Gson().toJson(detailsItem)

                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("item", detailsItemString)
                intent.putExtra("type", DetailsActivity.TYPE.LIST.toString())
                startActivity(intent)
            }
        }
        val adapter = SimpleItemListAdapter<ListItem, ListItemView<ListItem>>(onClickFunc,
                { ListItemView<ListItem>(R.layout.item_simple_list, bindFunc, baseContext, null) } );

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

    fun <T, TView : View>initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SimpleItemListAdapter<T, TView>) {
        recyclerView.addItemDecoration(SidesMarginDecoration(0))
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}
