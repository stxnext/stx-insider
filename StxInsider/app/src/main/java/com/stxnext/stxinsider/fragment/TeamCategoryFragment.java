package com.stxnext.stxinsider.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.adapter.SliderAdapter;
import com.stxnext.stxinsider.constant.Teams;
import com.stxnext.stxinsider.model.SliderItem;
import com.stxnext.stxinsider.model.TeamCategoryHeader;
import com.stxnext.stxinsider.view.MarginDecoration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryFragment extends Fragment {

    final String TAG = TeamCategoryFragment.class.getName();
    TeamCategoryHeader teamCategoryHeader;
    RecyclerView teamListRecyclerView;

    public TeamCategoryFragment teamCategoryHeader(TeamCategoryHeader teamCategoryHeader) {
        this.teamCategoryHeader = teamCategoryHeader;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_outer_layout, container, false);

        teamListRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_team_header_team_list);

        TextView relatedProjectsHeaderTV = ((TextView)view.findViewById(R.id.fragment_team_header_related_project_textview));
        ((TextView)view.findViewById(R.id.fragment_team_header_main_header)).setText(teamCategoryHeader.getHeader());
        ((TextView)view.findViewById(R.id.fragment_team_header_footer)).setText(teamCategoryHeader.getFooter());
        ImageView img = (ImageView)view.findViewById(R.id.fragment_team_header_image);
        LinearLayout outerLL = (LinearLayout)view.findViewById(R.id.team_header_outer_layout);
        try {
            InputStream file = getContext().getAssets().open(teamCategoryHeader.getImagePath());
            Drawable d = Drawable.createFromStream(file, null);
            img.setImageDrawable(d);

            if (teamCategoryHeader.getBackground() != null && !teamCategoryHeader.getBackground().isEmpty()) {
                InputStream backgFile = getContext().getAssets().open(teamCategoryHeader.getBackground());
                Drawable backDraw = Drawable.createFromStream(backgFile, null);
                outerLL.setBackground(backDraw);
            }
        } catch (IOException e) {
            Log.e(TAG, "Cannot read image from assets: " + e.toString());
        }

        SliderAdapter adapter = new SliderAdapter(getContext());
        for (SliderItem team : Teams.teams)
            if (team.getCategory() != null && teamCategoryHeader.getCategory() != null)
                if (team.getCategory().equals(teamCategoryHeader.getCategory()))
                    adapter.addItem(team);

        if (adapter.getItemCount() > 0)
            initializeRecyclerView(new LinearLayoutManager(getContext()), adapter);
        else
            relatedProjectsHeaderTV.setVisibility(View.GONE);

        return view;
    }

    public void initializeRecyclerView(LinearLayoutManager linearLayoutManager, SliderAdapter adapter) {
        teamListRecyclerView.addItemDecoration(new MarginDecoration(20));
        teamListRecyclerView.setHasFixedSize(true);
        teamListRecyclerView.setLayoutManager(linearLayoutManager);
        teamListRecyclerView.setAdapter(adapter);
    }
}
