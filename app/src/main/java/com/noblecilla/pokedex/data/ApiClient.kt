package com.noblecilla.pokedex.data

import com.noblecilla.pokedex.model.Pokemon
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private var services: ApiServices? = null

    fun build(): ApiServices? {
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://pogoapi.net/")
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()

        services = retrofit.create(ApiServices::class.java)

        return services as ApiServices
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ApiServices {

        @GET("/api/v1/pokemon_types.json")
        suspend fun all(): Response<List<Pokemon>>
    }
}
