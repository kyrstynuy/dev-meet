package com.kryzl.meetthedevs

import android.app.Application
import com.kryzl.meetthedevs.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTimber()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    private fun setupDagger() {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .context(applicationContext)
            .build()
        appComponent.inject(this)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
