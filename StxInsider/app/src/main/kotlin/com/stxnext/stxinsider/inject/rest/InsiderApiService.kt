package com.stxnext.stxinsider.inject.rest

import com.stxnext.stxinsider.model.SliderItem
import dagger.Module
import retrofit.Callback
import javax.inject.Inject

/**
 * Created by bkosarzycki on 01.03.16.
 */

@Module
class InsiderApiService {

    @Inject lateinit var service: InsiderApiResource

    @Inject constructor() {}


    fun getPhotosAsync(callback: Callback<List<SliderItem>>) {
        val call = service.getPhotos()
        call.enqueue(callback)
    }
}