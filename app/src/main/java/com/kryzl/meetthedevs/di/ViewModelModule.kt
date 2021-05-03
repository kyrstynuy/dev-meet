package com.kryzl.meetthedevs.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kryzl.meetthedevs.presentation.detail.DetailsViewModel
import com.kryzl.meetthedevs.presentation.addedit.AddEditViewModel
import com.kryzl.meetthedevs.presentation.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(listViewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditViewModel::class)
    abstract fun bindAddEditViewModel(addEditViewModel: AddEditViewModel): ViewModel

}
