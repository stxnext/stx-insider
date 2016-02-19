package com.stxnext.stxinsider.model;

import com.stxnext.stxinsider.R;
import com.stxnext.stxinsider.SliderActivity;

/**
 * Created by Łukasz Ciupa on 19.02.2016.
 */
public enum SliderActivityType {

    PORTFOLIO(R.string.portfolio),
    TEAM(R.string.team);

    private final int title;

    SliderActivityType(int title) {
        this.title = title;
    }

    public int getTitle() {
        return title;
    }
}
