package com.stxnext.stxinsider.inject.rest

import com.stxnext.stxinsider.model.SliderItem
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by bkosarzycki on 01.03.16.
 */
interface InsiderApiResource {

    @GET("teams")
    fun getTeams() : Call<List<SliderItem>>
}
