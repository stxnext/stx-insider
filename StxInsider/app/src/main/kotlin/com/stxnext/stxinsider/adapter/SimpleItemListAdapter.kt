package com.stxnext.stxinsider.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Toast
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.elementItemView.BaseItemView
import com.stxnext.stxinsider.view.model.ListItem
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase
import com.stxnext.stxinsider.viewmodel.ViewWrapper

/**
 * Created by bkosarzycki on 15.02.16.
 */

class SimpleItemListAdapter<T, TView : View>(
        clickListenerParam: (position: Int?, v : View) -> Unit,
        factoryParam : (viewType: Int) -> TView) : RecyclerViewAdapterBase<T, TView>(factoryParam), View.OnClickListener {

    val clickListener: (position: Int?, v : View) -> Unit = clickListenerParam

    override fun onClick(view: View?) {
        val position = (view as ListItemView<*>).position
        clickListener.invoke(position, view)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(viewHolder: ViewWrapper<TView>?, position: Int) {
        val view = viewHolder!!.view

        val itemToBind = items[position]
        (view as ListItemView<T>).bind(itemToBind, position, this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateItemView(parent: ViewGroup, viewType: Int): TView {
        val view = factory(viewType)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = layoutParams
        return view as TView
    }

    fun addItems(itemsParam : Collection<T>) {
        items.addAll(itemsParam)
    }

}