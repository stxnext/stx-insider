package com.stxnext.stxinsider.model

/**
 * Created by bkosarzycki on 22.01.16.
 */
class TeamCategoryHeader {
    var header: String? = null
    var imagePath: String? = null
    var footer: String? = null
    var category: Category? = null
    var background: String? = null

    fun header(header: String): TeamCategoryHeader {
        this.header = header
        return this
    }

    fun imagePath(imagePath: String): TeamCategoryHeader {
        this.imagePath = imagePath
        return this
    }

    fun footer(footer: String): TeamCategoryHeader {
        this.footer = footer
        return this
    }

    fun category(category: Category): TeamCategoryHeader {
        this.category = category
        return this
    }

    fun background(background: String): TeamCategoryHeader {
        this.background = background
        return this
    }
}
