package com.stxnext.stxinsider.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.model.TeamCategoryHeader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryFragment extends Fragment {

    final String TAG = TeamCategoryFragment.class.getName();
    TeamCategoryHeader teamCategoryHeader;

    public TeamCategoryFragment teamCategoryHeader(TeamCategoryHeader teamCategoryHeader) {
        this.teamCategoryHeader = teamCategoryHeader;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_outer_layout, container, false);

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


        return view;
    }
}
