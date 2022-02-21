package com.tunahanozatac.prayertime.network

import com.tunahanozatac.prayertime.util.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        fun getRetrofit(): PrayerService? {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(provideLoggingInterceptor()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PrayerService::class.java)
        }

        private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder().apply { addInterceptor(interceptor) }.build()

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}