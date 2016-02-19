package com.stxnext.stxinsider.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stxnext.stxinsider.fragment.PortfolioCategoryFragment;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class CategoriesFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    private final Context context;

    public CategoriesFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments.addAll(fragments);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Bundle bundle = fragments.get(position).getArguments();
        if (bundle != null) {
            int title = bundle.getInt(PortfolioCategoryFragment.CATEGORY_TAG, -1);
            if (title != -1) {
                return context.getString(title);
            }
        }
        return "";
    }
}
