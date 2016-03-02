package com.stxnext.stxinsider.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.stxnext.stxinsider.fragment.PortfolioCategoryFragment
import com.stxnext.stxinsider.fragment.TeamCategoryFragment

import java.util.ArrayList

/**
 * Created by bkosarzycki on 22.01.16.
 */
class SliderFragmentPagerAdapter(val context: Context, fm: FragmentManager, fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
    internal var fragments: MutableList<Fragment> = ArrayList()

    init {
        this.fragments.addAll(fragments)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        val bundle = fragments[position].arguments
            if (bundle != null) {
                val title = bundle.getInt(PortfolioCategoryFragment.CATEGORY_TAG, -1);
                if (title != -1)
                    return context.getString(title);
            }
        return ""
    }
}
