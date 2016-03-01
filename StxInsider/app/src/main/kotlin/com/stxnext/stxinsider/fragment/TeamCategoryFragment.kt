package com.stxnext.stxinsider.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.adapter.SliderAdapter
import com.stxnext.stxinsider.constant.Teams
import com.stxnext.stxinsider.model.SliderItem
import com.stxnext.stxinsider.model.TeamCategoryHeader
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.elementItemView.BaseItemView
import com.stxnext.stxinsider.view.elementItemView.ShortItemView
import com.stxnext.stxinsider.view.elementItemView.TallItemView

import java.io.IOException
import java.io.InputStream

/**
 * Created by bkosarzycki on 22.01.16.
 */
class TeamCategoryFragment(var teamCategoryHeader: TeamCategoryHeader) : Fragment() {

    internal val TAG = TeamCategoryFragment::class.java.name
    lateinit var teamListRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_team_outer_layout, container, false)

        teamListRecyclerView = view.findViewById(R.id.fragment_team_header_team_list) as RecyclerView

        val relatedProjectsHeaderTV = view.findViewById(R.id.fragment_team_header_related_project_textview) as TextView
        (view.findViewById(R.id.fragment_team_header_footer) as TextView).text = teamCategoryHeader.additionalDescr
        val outerLL = view.findViewById(R.id.team_header_outer_layout) as LinearLayout

        val adapter = SliderAdapter(context, ShortItemView::class.java,
                { ShortItemView(context, null) })
        for (team in Teams.teams)
//            if (team.category != null && teamCategoryHeader.category != null)
//                if (team.category == teamCategoryHeader.category)
                    adapter.addItem(team)

        if (adapter.itemCount > 0)
            initializeRecyclerView(LinearLayoutManager(context), adapter)
        else
            relatedProjectsHeaderTV.visibility = View.GONE

        return view
    }

    fun <T : BaseItemView>initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SliderAdapter<T>) {
        teamListRecyclerView.addItemDecoration(MarginDecoration(20))
        teamListRecyclerView.setHasFixedSize(true)
        teamListRecyclerView.layoutManager = linearLayoutManager
        teamListRecyclerView.adapter = adapter
    }
}
