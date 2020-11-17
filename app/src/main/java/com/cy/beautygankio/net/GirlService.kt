package com.cy.beautygankio.net

import androidx.lifecycle.LiveData
import com.cy.beautygankio.data.GankPage
import com.cy.beautygankio.data.Girl
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GirlService {

    @GET("/api/v2/data/category/Girl/type/Girl/page/{page}/count/{count}")
    suspend fun getGirls(@Path("page") page:Int,@Path("count") count:Int): GankPage


    companion object{
        const val BASE_URL = "https://gank.io/"

        fun create():GirlService{
            val logger =HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GirlService::class.java)
        }
    }
}