package com.mcsimf.reddittop.core.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_URL = "https://www.reddit.com/"

/**
 * @author Maksym Fedyay on 9/13/20 (mcsimf@gmail.com).
 */
object Api {

    val listings: RedditListingsApi

    init {
        val okHttpClient = OkHttpClient.Builder().let {

            it.addInterceptor() { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .header("User-Agent", "McSimF Reddit android client")
                        .build()
                )
            }

            it.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })

            it.build()
        }

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        this.listings = retrofit.create(RedditListingsApi::class.java)
    }

}