package com.stxnext.stxinsider.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.adapter.SliderAdapter
import com.stxnext.stxinsider.constant.Portfolio
import com.stxnext.stxinsider.model.Category
import com.stxnext.stxinsider.model.SliderItem
import com.stxnext.stxinsider.view.MarginDecoration
import com.stxnext.stxinsider.view.elementItemView.BaseItemView
import com.stxnext.stxinsider.view.elementItemView.ShortItemView
import com.stxnext.stxinsider.view.elementItemView.TallItemView

/**
 * Created by Bartosz Kosarzycki 23.02.16
 */
class PortfolioCategoryFragment : Fragment() {

    lateinit internal var portfolioCategory: Category
    lateinit internal var portfolioListRecyclerView: RecyclerView

    fun withCategory(category: Category): PortfolioCategoryFragment {
        this.portfolioCategory = category
        val bundle = Bundle()
        bundle.putInt(CATEGORY_TAG, category.intValue)
        this.arguments = bundle
        return this
    }

    @Suppress("SENSELESS_COMPARISON")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_portfolio_layout, container, false)

        portfolioListRecyclerView = view.findViewById(R.id.fragment_team_header_team_list) as RecyclerView

        val adapter = SliderAdapter(context, TallItemView::class.java)
        for (portfolio in Portfolio.portfolio)
            if (portfolio.category != null && portfolioCategory != null)
                if (portfolio.category == portfolioCategory)
                    adapter.addItem(portfolio)

        if (adapter.itemCount > 0)
            initializeRecyclerView(LinearLayoutManager(context), adapter)

        return view
    }

    fun <T : BaseItemView>initializeRecyclerView(linearLayoutManager: LinearLayoutManager, adapter: SliderAdapter<T>) {
        portfolioListRecyclerView.addItemDecoration(MarginDecoration(20))
        portfolioListRecyclerView.setHasFixedSize(true)
        portfolioListRecyclerView.layoutManager = linearLayoutManager
        portfolioListRecyclerView.adapter = adapter
    }

    companion object {
        val CATEGORY_TAG = "category"
        val TAG = PortfolioCategoryFragment::class.java.name
    }
}
