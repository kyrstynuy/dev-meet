package com.kryzl.meetthedevs.di

import com.kryzl.meetthedevs.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}
