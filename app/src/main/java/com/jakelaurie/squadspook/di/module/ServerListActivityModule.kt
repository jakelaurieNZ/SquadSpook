package com.jakelaurie.squadspook.di.module

import android.arch.lifecycle.ViewModelProvider
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ServerListActivityModule {
    @Provides
    fun provideServerListViewModelFactory(
            factory: ServerListViewModelFactory): ViewModelProvider.Factory = factory
}