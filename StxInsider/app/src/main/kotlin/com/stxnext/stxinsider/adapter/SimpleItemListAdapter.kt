package com.stxnext.stxinsider.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.TeamItemView
import com.stxnext.stxinsider.view.model.ListItem
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase
import com.stxnext.stxinsider.viewmodel.ViewWrapper

/**
 * Created by bkosarzycki on 15.02.16.
 */

class SimpleItemListAdapter<T, TView : View?>(contextParam: Context?) : RecyclerViewAdapterBase<T, TView>(), View.OnClickListener {

    val context : Context = contextParam!!

    override fun onClick(view: View?) {
        throw UnsupportedOperationException()
    }


    override fun onBindViewHolder(p0: ViewWrapper<TView>?, p1: Int) {
        //do nothing for now
    }

    override fun onCreateItemView(parent: ViewGroup?, viewType: Int): TView {
        val v = ListItemView(parent!!.context, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        return v as TView
    }

    fun addItems(itemsParam : Collection<T>) {
        items.addAll(itemsParam)
    }

}