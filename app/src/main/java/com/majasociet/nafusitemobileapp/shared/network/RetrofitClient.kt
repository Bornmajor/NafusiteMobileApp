package com.majasociet.nafusitemobileapp.shared.network

import com.majasociet.nafusitemobileapp.BuildConfig
import com.majasociet.nafusitemobileapp.features.profile.data.remote.api.CloudinaryApiService
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

   val API_KEY:String = BuildConfig.CLOUDINARY_API_KEY;
    val API_SECRET:String = BuildConfig.CLOUDINARY_API_SECRET;
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val credentials = Credentials.basic(
            API_KEY, API_SECRET
        )
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", credentials)
            .build()

        chain.proceed(authenticatedRequest)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private const val BASE_URL = "https://api.cloudinary.com/v1_1/yaal5uun/"

    val instance: CloudinaryApiService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(CloudinaryApiService::class.java)
    }
}