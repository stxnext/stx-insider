package com.stxnext.stxinsider.view.model

/**
 * Created by bkosarzycki on 16.02.16.
 */
data class DetailsItem<T>(val title: String, val subtitle: String, val replacingImagePath : String?, val content: T)