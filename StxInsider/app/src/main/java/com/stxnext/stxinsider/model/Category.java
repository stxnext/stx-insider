package com.stxnext.stxinsider.model;

import com.stxnext.stxinsider.R;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public enum Category {
    DATA_MINING (R.string.data_mining),
    FINANCIAL_ANALYSIS(R.string.financial_analysis),
    BANKING(R.string.banking), MARKETING(R.string.marketing),
    MOBILE (R.string.mobile),
    WEB (R.string.web);

    private final int name;

    Category(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }
}
