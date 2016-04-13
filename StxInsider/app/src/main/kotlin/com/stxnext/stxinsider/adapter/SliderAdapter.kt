package com.stxnext.stxinsider.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.TypeAdapterFactory
import com.stxnext.stxinsider.TeamDetailsActivity
import com.stxnext.stxinsider.model.SliderItem
import com.stxnext.stxinsider.view.elementItemView.BaseItemView
import com.stxnext.stxinsider.view.elementItemView.TallItemView
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase
import com.stxnext.stxinsider.viewmodel.ViewWrapper

/**
 * Created by bkosarzycki on 01.02.16.
 */
class SliderAdapter<T : BaseItemView>(private val mContext: Context, factoryParam : (viewType: Int) -> T) :
        RecyclerViewAdapterBase<SliderItem, T>(factoryParam), View.OnClickListener, BaseItemView.OnItemViewSeeMoreClickListener {

    internal val TAG = SliderAdapter::class.java.name
    var clickedElementIndex : Int? = null;
    val HEADER_TYPE : Int = 0;

    override fun onClick(v: View?) {
        val view = v as BaseItemView
        val item = view.item
        val intent = Intent(mContext, TeamDetailsActivity::class.java)
        intent.putExtra("item", Gson().toJson(item))
        mContext.startActivity(intent)
    }

    override fun onSeeMoreClick(position: Int) {
        clickedElementIndex = position
        notifyDataSetChanged()
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): T {
        Log.d(TAG, "onCreateItemView viewType: " + viewType)
        val v: T = factory(viewType)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        return v as T
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(viewHolder: ViewWrapper<T>, position: Int) {
        Log.d(TAG, "onBindViewHolder view type: " + getItemViewType(position))
        val view = viewHolder.view

        val itemToBind = items[position]
        var elementedClicked : Boolean = false;
        if (clickedElementIndex == position) elementedClicked = true
        view.bind(itemToBind, position, this, this, elementedClicked)
    }

    fun addItem(team: SliderItem) {
        items.add(team)
    }
}
