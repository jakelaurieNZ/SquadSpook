package com.jakelaurie.squadspook.di.module

import android.arch.lifecycle.ViewModelProvider
import com.jakelaurie.squadspook.annotations.OpenForTesting
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModelFactory
import dagger.Module
import dagger.Provides

@OpenForTesting
@Module
class ServerListActivityModule {
    @Provides
    fun provideServerListViewModelFactory(
            factory: ServerListViewModelFactory): ViewModelProvider.Factory = factory
}