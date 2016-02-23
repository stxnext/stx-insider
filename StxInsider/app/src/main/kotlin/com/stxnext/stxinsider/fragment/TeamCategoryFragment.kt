package com.stxnext.stxinsider.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

import java.io.IOException
import java.io.InputStream

/**
 * Created by bkosarzycki on 22.01.16.
 */
class TeamCategoryFragment : Fragment() {

    internal val TAG = TeamCategoryFragment::class.java.name
    lateinit var teamCategoryHeader: TeamCategoryHeader
    lateinit var teamListRecyclerView: RecyclerView

    //todo: change to backing properties in constructor declaration
    fun teamCategoryHeader(teamCategoryHeader: TeamCategoryHeader): TeamCategoryFragment {
        this.teamCategoryHeader = teamCategoryHeader
        return this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_team_outer_layout, container, false)

        teamListRecyclerView = view.findViewById(R.id.fragment_team_header_team_list) as RecyclerView

        val relatedProjectsHeaderTV = view.findViewById(R.id.fragment_team_header_related_project_textview) as TextView
        (view.findViewById(R.id.fragment_team_header_main_header) as TextView).text = teamCategoryHeader.header
        (view.findViewById(R.id.fragment_team_header_footer) as TextView).text = teamCategoryHeader.footer
        val img = view.findViewById(R.id.fragment_team_header_image) as ImageView
        val outerLL = view.findViewById(R.id.team_header_outer_layout) as LinearLayout
        try {
            val file = context.assets.open(teamCategoryHeader.imagePath)
            val d = Drawable.createFromStream(file, null)
            img.setImageDrawable(d)

            if (teamCategoryHeader.background != null && !teamCategoryHeader.background!!.isEmpty()) {
                val backgFile = context.assets.open(teamCategoryHeader.background)
                val backDraw = Drawable.createFromStream(backgFile, null)
                outerLL.background = backDraw
            }
        } catch (e: IOException) {
            Log.e(TAG, "Cannot read image from assets: " + e.toString())
        }

        val adapter = SliderAdapter(context)
        for (team in Teams.teams)
            if (team.category != null && teamCategoryHeader.category != null)
                if (team.category == teamCategoryHeader.category)
                    adapter.addItem(team)

        if (adapter.itemCount > 0)
            initializeRecyclerView(LinearLayoutManager(context), adapter)
        else
            relatedProjectsHeaderTV.visibility = View.GONE

        return view
    }

    fun initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SliderAdapter) {
        teamListRecyclerView.addItemDecoration(MarginDecoration(20))
        teamListRecyclerView.setHasFixedSize(true)
        teamListRecyclerView.layoutManager = linearLayoutManager
        teamListRecyclerView.adapter = adapter
    }
}
