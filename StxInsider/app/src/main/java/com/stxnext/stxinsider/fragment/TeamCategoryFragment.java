package com.stxnext.stxinsider.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.model.TeamCatergoryHeader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategoryFragment extends Fragment {

    final String TAG = TeamCategoryFragment.class.getName();
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
        ImageView img = (ImageView)view.findViewById(R.id.fragment_team_header_image);
        try {
            InputStream file = getContext().getAssets().open(teamCatergoryHeader.getImagePath());
            Drawable d = Drawable.createFromStream(file, null);
            img.setImageDrawable(d);
        } catch (IOException e) {
            Log.e(TAG, "Cannot read image from assets: " + e.toString());
        }


        return view;
    }
}
