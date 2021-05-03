package com.kryzl.meetthedevs.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }

    @Provides
    fun provideRetrofit(
        builder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder,
        @Named("apiBaseUrl") apiBaseUrl: String
    ):
        Retrofit =
            builder.client(okHttpClientBuilder.build())
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
}
