package com.kryzl.meetthedevs.di

import android.content.Context
import com.kryzl.meetthedevs.image.GlideImageHandler
import com.kryzl.meetthedevs.image.RemoteImageHandler
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ViewModule {

    @Provides
    @Singleton
    fun provideImageHandler(): RemoteImageHandler = GlideImageHandler()

}
