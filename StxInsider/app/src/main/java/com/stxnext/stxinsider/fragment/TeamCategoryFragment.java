package com.stxnext.stxinsider.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxnext.stxinsider.R;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_outer_layout, container, false);
        return view;
    }
}
