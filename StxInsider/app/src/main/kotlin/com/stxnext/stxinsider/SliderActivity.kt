package com.stxnext.stxinsider

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View

import com.google.common.collect.Lists
import com.stxnext.stxinsider.adapter.SliderFragmentPagerAdapter
import com.stxnext.stxinsider.constant.CategoryHeaders
import com.stxnext.stxinsider.constant.Categories
import com.stxnext.stxinsider.fragment.PortfolioCategoryFragment
import com.stxnext.stxinsider.fragment.TeamCategoryFragment
import com.stxnext.stxinsider.model.Category
import com.stxnext.stxinsider.model.SliderActivityType
import com.stxnext.stxinsider.model.TeamCategoryHeader

class SliderActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var fragmentAdapter: SliderFragmentPagerAdapter? = null
    private var type: SliderActivityType? = SliderActivityType.PORTFOLIO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        type = intent.getSerializableExtra(TYPE_TAG) as SliderActivityType
        if (type == null)
            type = SliderActivityType.PORTFOLIO

        supportActionBar!!.setTitle(type!!.intValue)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fragmentList = Lists.newArrayList<Fragment>()
        when (type) {
            SliderActivityType.PORTFOLIO -> for (portfolioCategory in Categories.categoryList)
                    fragmentList.add(PortfolioCategoryFragment().withCategory(portfolioCategory))
            SliderActivityType.TEAM -> for (teamCategoryHeader in CategoryHeaders.teams)
                    fragmentList.add(TeamCategoryFragment().teamCategoryHeader(teamCategoryHeader))
        }

        fragmentAdapter = SliderFragmentPagerAdapter(this, supportFragmentManager, fragmentList)
        val tabStrip = findViewById(R.id.sliding_tabs) as PagerTabStrip
        if (type == SliderActivityType.TEAM)
            tabStrip.visibility = View.GONE

        viewPager = findViewById(R.id.viewpager) as ViewPager
        viewPager!!.adapter = fragmentAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmField val TYPE_TAG = "type"
    }
}
