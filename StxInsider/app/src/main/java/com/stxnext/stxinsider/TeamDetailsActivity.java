package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.stxnext.stxinsider.adapter.TeamCategoriesFragmentPagerAdapter;
import com.stxnext.stxinsider.constant.TeamCategories;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;
import com.stxnext.stxinsider.model.Team;
import com.stxnext.stxinsider.model.TeamCategoryHeader;

import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {

    Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTeam = new Gson().fromJson(getIntent().getStringExtra("item"), Team.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
