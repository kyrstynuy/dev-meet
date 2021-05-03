package com.kryzl.meetthedevs.di

import com.kryzl.meetthedevs.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ConfigModule {

    @Provides
    @Named("apiBaseUrl")
    fun apiBaseUrl(): String = BuildConfig.API_BASE_URL

}
