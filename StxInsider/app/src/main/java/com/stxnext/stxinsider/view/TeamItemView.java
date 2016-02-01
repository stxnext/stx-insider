package com.stxnext.stxinsider.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.model.Team;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class TeamItemView extends FrameLayout {

    private Context mContext;
    private Team item;

    public TeamItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        removeAllViews();
        addView(LayoutInflater.from(context).inflate(R.layout.item_teams_list, this, false));
    }

    public void bind(Team item, int position, OnClickListener clickListener) {
        this.item = item;

        TextView nameTextView = (TextView) this.findViewById(R.id.item_teams_list_header);
        nameTextView.setText(item.getHeader());

        if (clickListener != null)
            this.setOnClickListener(clickListener);
    }

    public Team getItem() {
        return item;
    }
}
