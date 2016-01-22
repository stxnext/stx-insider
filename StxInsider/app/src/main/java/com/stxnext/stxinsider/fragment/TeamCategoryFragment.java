package com.stxnext.stxinsider.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.model.TeamCatergoryHeader;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryFragment extends Fragment {

    TeamCatergoryHeader teamCatergoryHeader;

    public TeamCategoryFragment teamCategoryHeader(TeamCatergoryHeader teamCatergoryHeader) {
        this.teamCatergoryHeader = teamCatergoryHeader;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_outer_layout, container, false);

        ((TextView)view.findViewById(R.id.fragment_team_header_main_header)).setText(teamCatergoryHeader.getHeader());
        ((TextView)view.findViewById(R.id.fragment_team_header_footer)).setText(teamCatergoryHeader.getFooter());

        return view;
    }
}
