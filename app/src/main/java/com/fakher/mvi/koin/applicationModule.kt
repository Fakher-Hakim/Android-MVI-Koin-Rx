package com.fakher.mvi.koin

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fakher.mvi.BuildConfig
import com.fakher.mvi.data.ApiService
import com.fakher.mvi.data.DataProvider
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { Gson() }

    single {

        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {

        val okHttpClient = OkHttpClient.Builder()
            .cache(null)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val gson: Gson = get()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single {
        DataProvider(get())
    }
}