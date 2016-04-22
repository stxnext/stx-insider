package com.stxnext.stxinsider.model

/**
 * Created by bkosarzycki on 01.02.16.
 */
class SliderItem {

    var header: String? = null
    var imagePath: String? = null
    var description: String? = null
    var category: Category? = null
    var imageResource: Int? = null

    fun header(header: String): SliderItem {
        this.header = header
        return this
    }

    fun imagePath(imagePath: String): SliderItem {
        this.imagePath = imagePath
        return this
    }

    fun description(description: String): SliderItem {
        this.description = description
        return this
    }

    fun category(category: Category): SliderItem {
        this.category = category
        return this
    }

    fun imageResource(imageResource: Int): SliderItem {
        this.imageResource = imageResource
        return this
    }
}
