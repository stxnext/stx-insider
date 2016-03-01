package com.stxnext.stxinsider.viewmodel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.stxnext.stxinsider.view.elementItemView.BaseItemView

import java.util.ArrayList

/**
 * Created by bkosarzycki on 12/29/15.
 */
abstract class RecyclerViewAdapterBase<T, V : View>(factoryParam: (Context, AttributeSet?) -> V) : RecyclerView.Adapter<ViewWrapper<V>>() {

    var items: MutableList<T> = ArrayList()
    val factory : (Context, AttributeSet?) -> V = factoryParam


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewWrapper<V> {
        return ViewWrapper(onCreateItemView(parent, viewType, factory))
    }

    protected abstract fun onCreateItemView(parent: ViewGroup, viewType: Int, factory: (Context, AttributeSet?) -> V ): V
}
