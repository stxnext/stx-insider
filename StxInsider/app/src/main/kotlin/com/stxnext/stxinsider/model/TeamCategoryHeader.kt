package com.stxnext.stxinsider.model

/**
 * Created by bkosarzycki on 22.01.16.
 */
class TeamCategoryHeader {
    var additionalDescr: String? = null

    fun additionalDescr(footer: String): TeamCategoryHeader {
        this.additionalDescr = footer
        return this
    }
}
