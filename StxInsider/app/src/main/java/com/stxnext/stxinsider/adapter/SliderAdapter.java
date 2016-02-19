package com.stxnext.stxinsider.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.stxnext.stxinsider.TeamDetailsActivity;
import com.stxnext.stxinsider.model.SliderItem;
import com.stxnext.stxinsider.view.TeamItemView;
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase;
import com.stxnext.stxinsider.viewmodel.ViewWrapper;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class SliderAdapter extends RecyclerViewAdapterBase<SliderItem, TeamItemView>
                    implements AdapterView.OnClickListener {


    private final Context mContext;

    public SliderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        TeamItemView view = (TeamItemView)v;
        SliderItem item = view.getItem();
        Intent intent = new Intent(mContext, TeamDetailsActivity.class);
        intent.putExtra("item", new Gson().toJson(item));
        mContext.startActivity(intent);
    }

    @Override
    protected TeamItemView onCreateItemView(ViewGroup parent, int viewType) {
        TeamItemView v = new TeamItemView(parent.getContext(), null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewWrapper<TeamItemView> viewHolder, int position) {
        TeamItemView view = viewHolder.getView();

        final SliderItem itemToBind = items.get(position);
        view.bind(itemToBind, position, null);
    }

    public void addItem(SliderItem team) {
        items.add(team);
    }
}
