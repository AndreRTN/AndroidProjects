package com.example.weatherapp.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {


    companion object {
        private val API_KEY = "d17950e6e6524773a6b160838220105"
        private lateinit var retrofit: Retrofit
        private val baseUrl = "https://api.weatherapi.com/v1/"

        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::retrofit.isInitialized) {

                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(
                        httpClient
                            .addInterceptor { chain ->
                                val url = chain
                                    .request()
                                    .url()
                                    .newBuilder()
                                    .addQueryParameter("key", API_KEY)
                                    .build()
                                chain.proceed(chain.request().newBuilder().url(url).build())
                            }
                            .build()
                    )
                    .build()

            }
            return retrofit
        }

        fun <T> createService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}