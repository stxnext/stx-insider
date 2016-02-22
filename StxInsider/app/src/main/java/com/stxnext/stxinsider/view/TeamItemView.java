package com.stxnext.stxinsider.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.model.SliderItem;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class TeamItemView extends FrameLayout {

    final String TAG = TeamItemView.class.getName();

    private Context mContext;
    private SliderItem item;

    public TeamItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        removeAllViews();
        addView(LayoutInflater.from(context).inflate(R.layout.item_teams_list, this, false));
    }

    public void bind(SliderItem item, int position, OnClickListener clickListener) {
        this.item = item;

        TextView nameTextView = (TextView) this.findViewById(R.id.item_teams_list_header);
        ImageView teamImageView = (ImageView) this.findViewById(R.id.item_teams_list_team_background);
//        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        nameTextView.setText(item.getHeader());
        description.setText(item.getDescription());

        try {
            InputStream file = getContext().getAssets().open(item.getImagePath());
            Drawable draw = Drawable.createFromStream(file, null);
            teamImageView.setImageDrawable(draw);
        } catch (IOException e) {
            Log.e(TAG, "Error creating team image: " + e.toString());
        }

        if (clickListener != null)
            this.setOnClickListener(clickListener);
    }

    public SliderItem getItem() {
        return item;
    }
}
