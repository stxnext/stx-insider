package com.stxnext.stxinsider.model

import com.stxnext.stxinsider.R

/**
 * Created by bkosarzycki on 01.02.16.
 */
enum class Category private constructor(val intValue: Int) {
    DATA_MINING(R.string.data_mining),
    FINANCIAL_ANALYSIS(R.string.financial_analysis),
    BANKING(R.string.banking), MARKETING(R.string.marketing),
    MOBILE(R.string.mobile),
    WEB(R.string.web)
}
