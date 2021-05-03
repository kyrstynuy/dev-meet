package com.kryzl.meetthedevs.di

import com.kryzl.meetthedevs.presentation.addedit.AddEditFragment
import com.kryzl.meetthedevs.presentation.detail.DetailsFragment
import com.kryzl.meetthedevs.presentation.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PresenterBindingModule {

    @ContributesAndroidInjector
    abstract fun listFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun detailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    abstract fun addEditFragment(): AddEditFragment

}
