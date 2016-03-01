package com.stxnext.stxinsider.inject

import android.content.Context
import com.squareup.okhttp.Cache
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Response
import com.stxnext.stxinsider.inject.rest.InsiderApiResource
import com.stxnext.stxinsider.util.isDeviceOnline
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import java.io.File
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import java.io.IOException

/**
 * Created by bkosarzycki on 01.03.16.
 */
@Module
class NetworkModule {

    private val INSIDER_API_URL = "https://intranet-staging.stxnext.pl/api/insider"

    @Provides
    @Singleton
    internal fun provideInsiderApiResource(retrofit: Retrofit): InsiderApiResource {
        return retrofit.create(InsiderApiResource::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(INSIDER_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(context: Context, cache: Cache): OkHttpClient {
        val okHttpClient = createOkHttpClientWithCache(context, cache)
        return okHttpClient
    }

    @Provides
    @Singleton
    internal fun provideOkHttpCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "rest_responses")
        val cache = Cache(httpCacheDirectory, 20 * 1024 * 1024.toLong())
        return cache
    }

    private fun createOkHttpClientWithCache(context: Context, cache: Cache): OkHttpClient {

        val okHttpClient = OkHttpClient()
        okHttpClient.cache = cache

        okHttpClient.interceptors().add(
            Interceptor { chain ->
                val originalRequest = chain.request()

                val hash = "NmY0ZGFkZDgtZDcwOC0xMWU1LTk4MDktNmM2MjZkMmZlMmU2"

                val request = originalRequest.newBuilder().header("X-Intranet-Insider", hash). build()
                val response = chain.proceed(request)
                response.newBuilder().build()
            }
        )

//        okHttpClient.interceptors().add(
//                Interceptor { chain ->
//                    val originalRequest = chain.request()
//
//                    val cacheHeaderValue = if (okHttpClient.isDeviceOnline(context))
//                        "public, max-age=2419200"
//                    else
//                        "public, only-if-cached, max-stale=2419200"
//
//                    val request = originalRequest.newBuilder().build()
//                    val response = chain.proceed(request)
//                    response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", cacheHeaderValue).build()
//                })
//        okHttpClient.networkInterceptors().add(
//                Interceptor { chain ->
//                    val originalRequest = chain.request()
//
//                    val cacheHeaderValue = if (okHttpClient.isDeviceOnline(context))
//                        "public, max-age=2419200"
//                    else
//                        "public, only-if-cached, max-stale=2419200"
//
//                    val request = originalRequest.newBuilder().build()
//                    val response = chain.proceed(request)
//                    response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", cacheHeaderValue).build()
//                })
        return okHttpClient
    }
}