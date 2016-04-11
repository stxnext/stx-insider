package com.stxnext.stxinsider.view.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by bkosarzycki on 16.02.16.
 */
data class DetailsContentList(var data: List<DetailsContentListRow>, var listID : Int, val localization: LatLng?, val address: String?)