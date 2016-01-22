package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.common.collect.Lists;
import com.stxnext.stxinsider.adapter.TeamCategoriesFragmentPagerAdapter;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;

import java.util.List;

public class SliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TeamCategoriesFragmentPagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<TeamCategoryFragment> fragmentList = Lists.<TeamCategoryFragment>newArrayList();
        fragmentList.add(new TeamCategoryFragment());
        fragmentList.add(new TeamCategoryFragment());

        fragmentAdapter = new TeamCategoriesFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList);
        PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.sliding_tabs);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
