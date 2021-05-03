package com.kryzl.meetthedevs.di

import android.app.Application
import android.content.Context
import com.kryzl.meetthedevs.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        ConfigModule::class,
        RouterModule::class,
        PresenterBindingModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        GatewayModule::class,
        ViewModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)

}
