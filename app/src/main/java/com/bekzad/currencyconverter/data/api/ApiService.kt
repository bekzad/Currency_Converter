package com.bekzad.currencyconverter.data.api

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://www.cbr-xml-daily.ru/"

/**
 * @author Бекзад Насирахунов 19/3/22.
 */

// Adapter factory to convert kotlin objects. Java reflect does not support Kotlin classes
private val moshi = Moshi.Builder()
                    .build()

interface CBRApiService {

    @GET("daily_json.js")
    suspend fun getAllCurrencies(): CurrencyResponse

    companion object {
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val api = retrofit.create(CBRApiService::class.java)
    }
}