package com.stxnext.stxinsider.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stxnext.stxinsider.fragment.TeamCategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoriesFragmentPagerAdapter extends FragmentPagerAdapter {

    List<TeamCategoryFragment> fragments = new ArrayList<>();

    public TeamCategoriesFragmentPagerAdapter(Context context, FragmentManager fm, List<TeamCategoryFragment> fragments) {
        super(fm);
        this.fragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
