package com.stxnext.stxinsider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.common.collect.Lists;
import com.stxnext.stxinsider.adapter.SliderFragmentPagerAdapter;
import com.stxnext.stxinsider.constant.CategoryHeaders;
import com.stxnext.stxinsider.constant.Categories;
import com.stxnext.stxinsider.fragment.PortfolioCategoryFragment;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;
import com.stxnext.stxinsider.model.Category;
import com.stxnext.stxinsider.model.SliderActivityType;
import com.stxnext.stxinsider.model.TeamCategoryHeader;

import java.util.List;

public class SliderActivity extends AppCompatActivity {

    public static final String TYPE_TAG = "type";
    public static final int PORTFOLIO_TYPE = 1;
    public static final int TEAMS_TYPE = 2;

    private ViewPager viewPager;
    private SliderFragmentPagerAdapter fragmentAdapter;
    private SliderActivityType type = SliderActivityType.PORTFOLIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        type = (SliderActivityType) getIntent().getSerializableExtra(TYPE_TAG);
        if (type == null)
            type = SliderActivityType.PORTFOLIO;

        getSupportActionBar().setTitle(type.getIntValue());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Fragment> fragmentList = Lists.<Fragment>newArrayList();
        switch (type) {
            case PORTFOLIO:
                for(Category portfolioCategory : Categories.categoryList)
                    fragmentList.add(new PortfolioCategoryFragment().withCategory(portfolioCategory));
                break;
            case TEAM:
                for (TeamCategoryHeader teamCategoryHeader : CategoryHeaders.teams)
                    fragmentList.add(new TeamCategoryFragment().teamCategoryHeader(teamCategoryHeader));
                break;
        }

        fragmentAdapter = new SliderFragmentPagerAdapter(this, getSupportFragmentManager(), fragmentList);
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
