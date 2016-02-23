package com.stxnext.stxinsider.model

import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.SliderActivity

/**
 * Created by bkosarzycki on 23.02.16.
 */
enum class SliderActivityType private constructor(val intValue: Int) {
    PORTFOLIO(R.string.portfolio),
    TEAM(R.string.team)
}
