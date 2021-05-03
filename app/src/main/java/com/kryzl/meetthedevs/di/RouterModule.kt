package com.kryzl.meetthedevs.di

import com.kryzl.meetthedevs.base.presentation.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule {

    @Provides
    @Singleton
    fun provideRouter(): Router = Router()
}
