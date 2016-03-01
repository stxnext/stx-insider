package com.stxnext.stxinsider.inject.rest

import android.util.Log
import com.stxnext.stxinsider.model.SliderItem
import dagger.Module
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

/**
 * Created by bkosarzycki on 01.03.16.
 */

@Module
class InsiderApiService {

    @Inject lateinit var service: InsiderApiResource

    @Inject constructor() {}


    fun getTeamsAsync(callback: Callback<List<SliderItem>>) {
        val call = service.getPhotos()
        call.enqueue(callback)
    }

    fun getTeamsAsync(onResponse: (List<SliderItem>) -> Unit, onError: () -> Unit)  {
        val callback = object : retrofit.Callback<List<SliderItem>> {
            override fun onResponse(p0: Response<List<SliderItem>>?, p1: Retrofit?) {
                onResponse.invoke(p0!!.body());
            }

            override fun onFailure(p0: Throwable?) {
                Log.e(InsiderApiService::class.java.simpleName, "Error in REST call: " + p0.toString())
                onError.invoke()
            }
        }

        val call = service.getPhotos()
        call.enqueue(callback)
    }
}