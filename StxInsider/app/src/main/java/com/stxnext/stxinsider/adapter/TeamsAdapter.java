package com.stxnext.stxinsider.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.stxnext.stxinsider.model.Team;
import com.stxnext.stxinsider.view.TeamItemView;
import com.stxnext.stxinsider.viewmodel.RecyclerViewAdapterBase;
import com.stxnext.stxinsider.viewmodel.ViewWrapper;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class TeamsAdapter extends RecyclerViewAdapterBase<Team, TeamItemView>
                    implements AdapterView.OnClickListener {


    @Override
    public void onClick(View v) {
        TeamItemView view = (TeamItemView)v;
        Team item = view.getItem();
        //SomeActivity_
        //        .start();
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

        final Team itemToBind = items.get(position);
        view.bind(itemToBind, position, this);
    }

    public void addItem(Team team) {
        items.add(team);
    }
}
