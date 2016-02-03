package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.common.collect.Lists;
import com.stxnext.stxinsider.adapter.TeamCategoriesFragmentPagerAdapter;
import com.stxnext.stxinsider.constant.TeamCategories;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;
import com.stxnext.stxinsider.model.TeamCategoryHeader;

import java.util.List;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
