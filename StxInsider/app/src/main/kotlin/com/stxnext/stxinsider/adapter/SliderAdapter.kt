package com.stxnext.stxinsider.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.stxnext.stxinsider.TeamDetailsActivity
import com.stxnext.stxinsider.model.SliderItem
import com.stxnext.stxinsider.view.elementItemView.BaseItemView
import com.stxnext.stxinsider.view.elementItemView.TallItemView
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase
import com.stxnext.stxinsider.viewmodel.ViewWrapper

/**
 * Created by bkosarzycki on 01.02.16.
 */
class SliderAdapter<T : BaseItemView>(private val mContext: Context, entityClassParam: Class<T>) : RecyclerViewAdapterBase<SliderItem, T>(), View.OnClickListener {

    val entityClassParam: Class<T> = entityClassParam

    override fun onClick(v: View?) {
        val view = v as BaseItemView
        val item = view.item
        val intent = Intent(mContext, TeamDetailsActivity::class.java)
        intent.putExtra("item", Gson().toJson(item))
        mContext.startActivity(intent)
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): T {
       // var entity: T = entityClassParam.newInstance() //todo: newInstance with args!!!
        val v = TallItemView(parent.context, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        return v as T
    }

    override fun onBindViewHolder(viewHolder: ViewWrapper<T>, position: Int) {
        val view = viewHolder.view

        val itemToBind = items[position]
        view.bind(itemToBind, position, this)
    }

    fun addItem(team: SliderItem) {
        items.add(team)
    }
}
