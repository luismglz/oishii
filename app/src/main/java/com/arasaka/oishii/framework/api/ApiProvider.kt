package com.arasaka.oishii.framework.api

import com.arasaka.oishii.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiProvider @Inject constructor() {
    private var retrofit: Retrofit

    init {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG){
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            httpClientBuilder.addInterceptor(logging)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient().withNullSerialization())
            .client(httpClientBuilder.build())
            .build()
    }

    fun <S> getEndpoint(serviceClass: Class<S>): S = retrofit.create(serviceClass)
}