package com.stxnext.stxinsider.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Toast
import com.stxnext.stxinsider.view.ListItemView
import com.stxnext.stxinsider.view.TeamItemView
import com.stxnext.stxinsider.view.model.ListItem
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase
import com.stxnext.stxinsider.viewmodel.ViewWrapper

/**
 * Created by bkosarzycki on 15.02.16.
 */

class SimpleItemListAdapter<T, TView : View?>(
        bindFuncParam : (baseView: FrameLayout, item: T, position: Integer, clickListener: View.OnClickListener) -> Unit,
        contextParam: Context?) : RecyclerViewAdapterBase<T, TView>(), View.OnClickListener {

    val bindFunc = bindFuncParam
    val context : Context = contextParam!!

    override fun onClick(view: View?) {
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onBindViewHolder(viewHolder: ViewWrapper<TView>?, position: Int) {
        val view = viewHolder!!.view

        val itemToBind = items[position]
        (view as ListItemView<T>).bind(itemToBind as T, position as Integer, this)
    }

    override fun onCreateItemView(parent: ViewGroup?, viewType: Int): TView {
        val v = ListItemView<T>(bindFunc ,parent!!.context, null)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        return v as TView
    }

    fun addItems(itemsParam : Collection<T>) {
        items.addAll(itemsParam)
    }

}