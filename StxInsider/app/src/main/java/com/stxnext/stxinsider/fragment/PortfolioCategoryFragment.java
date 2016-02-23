package com.stxnext.stxinsider.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.adapter.SliderAdapter;
import com.stxnext.stxinsider.constant.Portfolio;
import com.stxnext.stxinsider.model.Category;
import com.stxnext.stxinsider.model.SliderItem;
import com.stxnext.stxinsider.view.MarginDecoration;

/**
 * Created by Łukasz Ciupa on 19.02.2016.
 */
public class PortfolioCategoryFragment extends Fragment {

    Category portfolioCategory;
    final String TAG = TeamCategoryFragment.class.getName();
    RecyclerView portfolioListRecyclerView;
    public static final String CATEGORY_TAG = "category";

    public PortfolioCategoryFragment portfolioCategory(Category portfolioCategory) {
        this.portfolioCategory = portfolioCategory;
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_TAG, portfolioCategory.getIntValue());
        this.setArguments(bundle);
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_layout, container, false);

        portfolioListRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_team_header_team_list);

        SliderAdapter adapter = new SliderAdapter(getContext());
        for (SliderItem portfolio : Portfolio.portfolio)
            if (portfolio.getCategory() != null && portfolioCategory != null)
                if (portfolio.getCategory().equals(portfolioCategory))
                    adapter.addItem(portfolio);

        if (adapter.getItemCount() > 0)
            initializeRecyclerView(new LinearLayoutManager(getContext()), adapter);

        return view;
    }

    public void initializeRecyclerView(LinearLayoutManager linearLayoutManager, SliderAdapter adapter) {
        portfolioListRecyclerView.addItemDecoration(new MarginDecoration(20));
        portfolioListRecyclerView.setHasFixedSize(true);
        portfolioListRecyclerView.setLayoutManager(linearLayoutManager);
        portfolioListRecyclerView.setAdapter(adapter);
    }

}
