package com.example.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Service Factory to generate all interceptors together with the Api Service
 */
object ApiServiceFactory {

    private const val BASE_URL: String = "http://www.omdbapi.com/"

    fun makeAPiService(isDebug: Boolean , moshi: Moshi): OmdbService {
        val okHttpClient: OkHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug)
        )
        return makeAPiService(okHttpClient, moshi)
    }

    private fun makeAPiService(okHttpClient: OkHttpClient, moshi: Moshi): OmdbService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(OmdbService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
